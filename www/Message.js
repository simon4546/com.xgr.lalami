 var exec = require('cordova/exec');
    var platform = require('cordova/platform');
    module.exports = {
        browser: function (url,ordid,ordfee,dealtitle,success,fail) {
            console.log(url);
            exec(success, fail, "Message", "alipay", [url,ordid,ordfee,dealtitle]);
        },
        comic: function (message,duration,position) {
            exec(null, null, "Message", "comic", [message,duration||'short',position||'bottom']);
        },
        chat: function (username,objectId) {
            exec(null, null, "Message", "chat", [username,objectId]);
        },
        recent: function () {

            exec(null, null, "Message", "recent", []);
        }
    };