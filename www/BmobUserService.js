    var exec = require('cordova/exec');
    var platform = require('cordova/platform');
    module.exports = {
        login: function (username,password,success,fail) {
            if (navigator.connection.type != 'unknown')
                exec(success, fail, "BmobUserService", "login", [username,password]);
            else
                success();
        },
        sendMsg: function (objectId,msg,success,fail) {
            if (navigator.connection.type != 'unknown')
                exec(success, fail, "BmobUserService", "sendMsg", [objectId,msg]);
            else
                success();
        },
        loginOut: function (success,fail) {
            exec(success, fail, "BmobUserService", "loginOut", []);
        },
        getObjectId: function (success,fail) {
            exec(success, fail, "BmobUserService", "getObjectId", []);
        },
        resetPassword: function (email,success,fail) {
            exec(success, fail, "BmobUserService", "resetPassword", [email]);
        },
        reg: function (username,password,avatar,success,fail) {
            if (navigator.connection.type != 'unknown')
            exec(success, fail, "BmobUserService", "reg", [username,password,avatar]);
            else
                success('xxxxxxxxxxxxxxxx');
        }
    };
