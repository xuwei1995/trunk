/*
 * 扩展jquery 插件，将表单中的表单组装成对象
*/
(function($) {
	
	// 扩展jquery 插件，将表单中的表单组装成对象
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		var str = this.serialize();
		$(array).each(
				function() {
					if (serializeObj[this.name]) {
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [
									serializeObj[this.name], this.value ];
						}
					} else {
						serializeObj[this.name] = this.value;
					}
				});
		return serializeObj;
	};
	

	// 扩展jquery 插件，将表单中的表单组装成对象 包含disable的元素
	$.fn.serializeJsonContaisDisable = function() {
		var serializeObj = {};
		
		
		this.find(":disabled").each(function() {
			if (serializeObj[this.name]) {
				if ($.isArray(serializeObj[this.name])) {
					serializeObj[this.name].push(this.value);
				} else {
					serializeObj[this.name] = [
							serializeObj[this.name], this.value ];
				}
			} else {
				serializeObj[this.name] = this.value;
			}
		});
		
		var array = this.serializeArray();
		var str = this.serialize();
		$(array).each(
				function() {
					if (serializeObj[this.name]) {
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [
									serializeObj[this.name], this.value ];
						}
					} else {
						serializeObj[this.name] = this.value;
					}
				});
		return serializeObj;
	};
	
	
	
})(jQuery);