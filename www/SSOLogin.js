 var exec = require('cordova/exec');
 var platform = require('cordova/platform');
 module.exports = {
     loginQQInfo: function(success, fail) {
         exec(success, fail, "SSOLogin", "loginQQInfo", []);
     },
     loginWeiXinInfo: function(success, fail) {
         exec(success, fail, "SSOLogin", "loginWeiXinInfo", []);
     },
     getSinaWeiboInfo: function(success, fail) {
         exec(success, fail, "SSOLogin", "getSinaWeiboInfo", []);
     }
 };