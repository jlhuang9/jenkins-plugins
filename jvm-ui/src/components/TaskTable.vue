<template>
  <div class="task">
    <el-table
      :data="tableData"
      style="width: 100%"
      empty-text="no data"
    >
      <el-table-column
        prop="name"
        label="name"
        width="180">
      </el-table-column>
      <el-table-column
        prop="number"
        label="number">
      </el-table-column>
      <el-table-column
        prop="timestamp"
        label="timestamp">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="operation"
        width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">details</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="prev, pager, next"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>

  import taskApi from "../ajax/taskApi";

  export default {
    name: "TaskTable",
    data() {
      return {
        tableData: [],
        total: 0
      }
    },
    created() {
      this.query();
    },
    methods: {
      query() {
        taskApi.pageTaskQuery({}, data => {
          this.tableData = data.rows;
          this.total = data.total;
        })
      },
      handleClick(row) {
        let routePath = '/Console';
        if (row) {
          routePath += "?name=" + row.name + "&number=" + row.number;
        }
        this.$router.push({path:routePath});
      }

    }
  }
</script>

<style scoped>

</style>
