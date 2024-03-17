<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import $ from 'jquery'
export default {
  name: 'App',
  data () {
    return {
      serverData: 'no data'
    }
  },
  methods: {
    getData () { // a test function
      $.ajax({
        type: 'GET', // 222.205.124.205
        url: 'http://localhost:8081/Hello/getperson/33333',
        dataType: 'json',
        crossDomain: true,
        xhrFields: {
          withCredentials: true
        },
        data: {},
        success: (result) => {
          this.serverData = JSON.stringify(result);
        },
        error: function () {
          alert('error');
        }
      })
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 20px;
  margin-bottom: 20px;
}
.router-link {
  float: left;
  font-weight: normal;
  font-family:"Microsoft YaHei", "Microsoft JhengHei Light", Helvetica, sans-serif;
  cursor: pointer;
  background: #abbac3;
  color: #ffffff;
  font-size: 16px;
  padding: 10px 20px 10px 20px;
  border: solid #abbac3 1px;
  text-decoration: none;
  margin: 5px 2px 10px 2px;
}
.router-link:hover {
  color: #ffffff;
  background: #888888;
  border: solid #888888 1px;
  text-decoration: none;
}
</style>
