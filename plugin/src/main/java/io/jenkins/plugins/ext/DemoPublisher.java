package io.jenkins.plugins.ext;

import com.alibaba.fastjson.JSONObject;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.console.AnnotatedLargeText;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import io.jenkins.plugins.ext.entity.TaskEntity;
import io.jenkins.plugins.ext.util.HttpUtils;
import jenkins.tasks.SimpleBuildStep;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huangchengqian
 */
public class DemoPublisher extends Publisher implements SimpleBuildStep {


    private final String name;
    private boolean useFrench;
    private static final ExecutorService threadPool;
    static {
        threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "callbackExecutor_" + this.threadIndex.incrementAndGet());
            }
        });
    }

    @DataBoundConstructor
    public DemoPublisher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isUseFrench() {
        return useFrench;
    }

    @DataBoundSetter
    public void setUseFrench(boolean useFrench) {
        this.useFrench = useFrench;
    }


    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        threadPool.execute(() -> {
            String name = build.getProject().getName();
            int number = build.getNumber();
            long timeInMillis = build.getTimeInMillis();
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setName(name);
            taskEntity.setNumber(number);
            taskEntity.setTimestamp(timeInMillis);
            taskEntity.setType(TaskEntity.START_TYPE);
            pushData(taskEntity);
            AnnotatedLargeText logText;
            long pos = 0;
            do {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                logText = build.getLogText();
                try {
                    long srcPos = pos;
                    pos = logText.writeLogTo(pos, byteArrayOutputStream);
                    pushDataSplitLines(byteArrayOutputStream, taskEntity, 100, build.getCharset().name(), srcPos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // TODO should sleep as in Run.writeWholeLogTo
            } while (!logText.isComplete());
        });
        return true;
    }

    private void pushDataSplitLines(ByteArrayOutputStream byteArrayOutputStream, TaskEntity taskEntity, int splitLines, String charset, long pos) throws UnsupportedEncodingException {
        byte[] bytes = byteArrayOutputStream.toByteArray();
        int length = bytes.length;
        int tempOffset = 0;
        int offset = 0;
        int linecount = 0;
        byte[] temp = null;
        while (offset < length) {
            if (bytes[offset] == "\n".getBytes(charset)[0]) {
                linecount++;
            }
            offset++;

            if ((linecount + 1) % (splitLines + 1) == 0) {
                temp = new byte[offset - tempOffset];
                System.arraycopy(bytes, tempOffset, temp, 0, offset - tempOffset);
                tempOffset = offset;
                linecount += 1;
                taskEntity.setLog(new String(temp,charset));
                taskEntity.setOffset(pos+offset);
                taskEntity.setType(TaskEntity.BUILDING_TYPE);
                pushData(taskEntity);
            }
        }
        if (offset > tempOffset) {
            temp = new byte[offset - tempOffset];
            System.arraycopy(bytes, tempOffset, temp, 0, offset - tempOffset);
            taskEntity.setLog(new String(temp,charset));
            taskEntity.setOffset(pos + offset);
            taskEntity.setType(TaskEntity.BUILDING_TYPE);
            pushData(taskEntity);
        }
    }

    private void pushData(TaskEntity taskEntity) {
        try {
            String domain = DESCRIPTOR.getDomain();
            HttpUtils.httpPostWithJSON(domain + "/build_data", JSONObject.toJSONString(taskEntity));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener)  {
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

    @Extension
    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();


}
