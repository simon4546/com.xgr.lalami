 var exec = require('cordova/exec');
    var platform = require('cordova/platform');
    module.exports = {
        loginQQInfo: function (url,ordid,ordfee,dealtitle,success,fail) {
            exec(success, fail, "SSOLogin", "loginQQInfo", [url,ordid,ordfee,dealtitle]);
        },
        loginWeiXinInfo: function (message,duration,position) {
            exec(null, null, "SSOLogin", "loginWeiXinInfo", [message,duration||'short',position||'bottom']);
        },
        getSinaWeiboInfo: function (username,objectId) {
            exec(null, null, "SSOLogin", "getSinaWeiboInfo", [username,objectId]);
        }
    };