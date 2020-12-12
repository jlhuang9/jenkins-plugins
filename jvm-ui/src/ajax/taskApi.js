import API from "./fetch";
export default {
  query(data, success){
    API.get('/report/query', data, success)
  },

  pageQuery(data, success){
    API.get('/report/pageQuery', data, success)
  },

  pageTaskQuery(data, success){
    API.get('/report/pageTaskQuery', data, success)
  },
  getConsoleLog(data, success){
    API.get('/report/getConsoleLog', data, success)
  }
}
