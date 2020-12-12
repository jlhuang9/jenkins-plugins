package io.jenkins.plugins.ext;

import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;
import hudson.util.FormValidation;
import io.jenkins.plugins.ext.util.HttpUtils;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;

/**
 * @author huangchengqian
 * @date 2020-12-10 12:45
 **/
public final class DescriptorImpl extends
        BuildStepDescriptor<Publisher> {

    private String domain;

    public DescriptorImpl() {
        super(DemoPublisher.class);
        load();
    }

    @Override
    public Publisher newInstance(StaplerRequest req, JSONObject formData)
            throws FormException {
        return super.newInstance(req, formData);
    }

    @Override
    public boolean isApplicable(Class<? extends AbstractProject> aClass) {
        return true;
    }

    public FormValidation doCheckDomain(
            @QueryParameter String domain) {
        try {
            HttpUtils.httpGet(domain + "/health");
        } catch (IOException e) {
            return FormValidation.error(e.getMessage());
        }
        return FormValidation.ok("domain validation successful！");
    }


    @Override
    public boolean configure(StaplerRequest req, JSONObject formData)
            throws FormException {
        req.bindParameters(this);
        this.domain = formData.getString("domain");
        save();
        return super.configure(req, formData);
    }

    @Override
    public String getDisplayName() {
        return "Console output demo";
    }

    public String getDomain() {
        return domain;
    }
}
