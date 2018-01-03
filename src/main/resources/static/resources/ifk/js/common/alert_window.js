/**
 * 页面信息提示框
 */

//jQuery Alert Dialogs Plugin
//
// Version 1.1
//
// Cory S.N. LaViska
// A Beautiful Site (http://abeautifulsite.net/)
// 14 May 2009
//
// Visit http://abeautifulsite.net/notebook/87 for more information
//
// Usage:
//		jAlert( alertType, message, [title, callback] ),top.jAlert(alertType, 'ddd','xxx',function(){alert('xxx')})
//              alertType(error, success, warning, common)
//		jConfirm( message, [title, callback] ),top.jConfirm('ddd','xxx',function(d){alert('xxx'+d)})
//		jPrompt( message, [value, title, callback] ),top.jConfirm('ddd','xxx',function(d){alert('xxx'+d)})
// 
// History:
//
//		1.00 - Released (29 December 2008)
//
//		1.01 - Fixed bug where unbinding would destroy all resize events
//
// License:
// 
// This plugin is dual-licensed under the GNU General Public License and the MIT License and
// is copyright 2008 A Beautiful Site, LLC. 
//
(function ($) {
	
    $.alerts = {

        // These properties can be read/written by accessing $.alerts.propertyName from your scripts at any time

        verticalOffset: -75,                // vertical offset of the dialog from center screen, in pixels
        horizontalOffset: 0,                // horizontal offset of the dialog from center screen, in pixels/
        repositionOnResize: true,           // re-centers the dialog on window resize
        overlayOpacity: .4,                	// transparency level of overlay
        overlayColor: '#FFF',               // base color of overlay
        draggable: true,                    // make the dialogs draggable (requires UI Draggables plugin)
        okButton: JSCOMMON_yes,         	// YES
        cancelButton: JSCOMMON_no, 			// NO
//        okButton: JSCOMMON_ok,         	// text for the OK button
//        cancelButton: JSCOMMON_cancel, 	// text for the Cancel button
        alertButton: JSCOMMON_ok,   		// text for the alertbutton
        dialogClass: null,                  // if specified, this class will be applied to all dialogs
        language: 'zh_CN',

        // Public methods

        alert: function (alertType, message, title, callback, opts) {
            if (title == null) title = 'Alert';
            $.alerts._show(title, message, null, 'alert', function (result) {
                if (callback) callback(result);
            }, opts, alertType);
        },

        confirm: function (message, title, callback, opts) {
            if (title == null) title = 'Confirm';
            $.alerts._show(title, message, null, 'confirm', function (result) {
                if (callback) callback(result);
            }, opts);
        },

        prompt: function (message, value, title, callback, opts) {
            if (title == null) title = 'Prompt';
            $.alerts._show(title, message, value, 'prompt', function (result) {
                if (callback) callback(result);
            }, opts);
        },

        // Private methods

        _show: function (title, msg, value, type, callback, opts, alertType) {
            //add by wangliang
            if (typeof (title) == 'object') {
                if ($.alerts.language == 'en_US') {
                    title = title.en;
                } else if ($.alerts.language == 'zh_CN') {
                    title = title.cn;
                } else {
                    alert('invalid title');
                }
            }
            if (typeof (msg) == 'object') {
                if ($.alerts.language == 'en_US') {
                    msg = msg.en;
                } else if ($.alerts.language == 'zh_CN') {
                    msg = msg.cn;
                } else {
                    alert('invalid msg');
                }
            }
            function getOkButton() {
                if (opts && opts.okButton) {
                    return opts.okButton;
                }
                return $.alerts.okButton;
            }
            function getCancelButton() {
                if (opts && opts.cancelButton) {
                    return opts.cancelButton;
                }
                return $.alerts.cancelButton;
            }
            function getAlertButton() {
                if (opts && opts.alertButton) {
                    return opts.alertButton;
                }
                return $.alerts.alertButton;
            }

            $.alerts._hide();
            $.alerts._overlay('show');

            $("BODY").append(
			  '<div id="popup_container">' +
			    '<div id="popup_title"></div>' +
			    '<div id="popup_content">' +
			      '<div id="popup_message"></div>' +
				'</div>' +
			  '</div>');

            if ($.alerts.dialogClass) $("#popup_container").addClass($.alerts.dialogClass);

            // IE6 Fix
            // var pos = ($.browser.msie && parseInt($.browser.version) <= 6) ? 'absolute' : 'fixed';

            $("#popup_container").css({
                position: 'fixed',
                zIndex: 99999
            });

            $("#popup_title").html("&nbsp;&nbsp;" + title);
            //$("#popup_content").addClass(type);
            if (type == "alert") {
                $("#popup_content").addClass(alertType);
            } else {
                $("#popup_content").addClass(type);
            }
            $("#popup_message").text(msg);
            $("#popup_message").html($("#popup_message").text().replace(/\n/g, '<br />'));

            $("#popup_container").css({
                minWidth: $("#popup_container").outerWidth(),
                maxWidth: $("#popup_container").outerWidth()
            });

            $.alerts._reposition();
            $.alerts._maintainPosition(true);

            switch (type) {
                case 'alert':
                    $("#popup_message").after('<div id="popup_panel"><input type="button" value="' + getAlertButton() + '" id="popup_ok" /></div>');
                    $("#popup_ok").click(function () {
                        $.alerts._hide();
                        callback(true);
                    });
                    $("#popup_ok").focus().keypress(function (e) {
                        if (e.keyCode == 13 || e.keyCode == 27) $("#popup_ok").trigger('click');
                    });
                    break;
                case 'confirm':
                    $("#popup_message").after('<div id="popup_panel"><input type="button" value="' + getOkButton() + '" id="popup_ok" /><input type="button" value="' + getCancelButton() + '" id="popup_cancel" /></div>');
                    $("#popup_ok").click(function () {
                        $.alerts._hide();
                        if (callback) callback(true);
                    });
                    $("#popup_cancel").click(function () {
                        $.alerts._hide();
                        if (callback) callback(false);
                    });
                    $("#popup_ok").focus();
                    $("#popup_ok, #popup_cancel").keypress(function (e) {
                        if (e.keyCode == 13) $("#popup_ok").trigger('click');
                        if (e.keyCode == 27) $("#popup_cancel").trigger('click');
                    });
                    break;
                case 'prompt':
                    $("#popup_message").append('<br /><input type="text" size="30" id="popup_prompt" />').after('<div id="popup_panel"><input type="button" value="' + getOkButton() + '" id="popup_ok" /> <input type="button" value="' + getCancelButton() + '" id="popup_cancel" /></div>');
                    $("#popup_prompt").width($("#popup_message").width());
                    $("#popup_ok").click(function () {
                        var val = $("#popup_prompt").val();
                        $.alerts._hide();
                        if (callback) callback(val);
                    });
                    $("#popup_cancel").click(function () {
                        $.alerts._hide();
                        if (callback) callback(null);
                    });
                    $("#popup_prompt, #popup_ok, #popup_cancel").keypress(function (e) {
                        if (e.keyCode == 13) $("#popup_ok").trigger('click');
                        if (e.keyCode == 27) $("#popup_cancel").trigger('click');
                    });
                    if (value) $("#popup_prompt").val(value);
                    $("#popup_prompt").focus().select();
                    break;
            }

            // Make draggable
            if ($.alerts.draggable) {
                try {
                    $("#popup_container").draggable({ handle: $("#popup_title") });
                    $("#popup_title").css({ cursor: 'move' });
                } catch (e) { /* requires jQuery UI draggables */ }
            }
        },

        _hide: function () {
            $("#popup_container").remove();
            $.alerts._overlay('hide');
            $.alerts._maintainPosition(false);
        },

        _overlay: function (status) {
            switch (status) {
                case 'show':
                    $.alerts._overlay('hide');
                    $("BODY").append('<div id="popup_overlay"></div>');
                    $("#popup_overlay").css({
                        position: 'absolute',
                        zIndex: 99998,
                        //width: '100%',
                        width: $(document).width(),
                        height: $(document).height(),
                        //background: $.alerts.overlayColor,
                        opacity: $.alerts.overlayOpacity
                    });
                    break;
                case 'hide':
                    $("#popup_overlay").remove();
                    break;
            }
        },

        _reposition: function () {
            var top = (($(window).height() / 2) - ($("#popup_container").outerHeight() / 2)) + $.alerts.verticalOffset;
            var left = (($(window).width() / 2) - ($("#popup_container").outerWidth() / 2)) + $.alerts.horizontalOffset;
            if (top < 0) top = 0;
            if (left < 0) left = 0;

            // IE6 fix
            // if ($.browser.msie && parseInt($.browser.version) <= 6) top = top + $(window).scrollTop();

            $("#popup_container").css({
                top: top + 'px',
                left: left + 'px'
            });
            $("#popup_overlay").height($(document).height());
        },

        _maintainPosition: function (status) {
            if ($.alerts.repositionOnResize) {
                switch (status) {
                    case true:
                        $(window).bind('resize', $.alerts._reposition);
                        break;
                    case false:
                        $(window).unbind('resize', $.alerts._reposition);
                        break;
                }
            }
        }

    };

    // Shortuct functions
    //alertType error,success,warning,common
    jAlert = function (alertType, message, title, callback, opts) {
        $.alerts.alert(alertType, message, title, callback, opts);
    };

    jPrompt = function (message, value, title, callback, opts) {
        $.alerts.prompt(message, value, title, callback, opts);
    };

    jConfirm = function (message, title, callback, opts) {
        $.alerts.confirm(message, title, callback, opts);
    };
})(jQuery);


