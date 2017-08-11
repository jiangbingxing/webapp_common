$(function() {
	initComponentType(4)
	/*增加快捷检查第一步的组件样式*/
	$(".yaezakura-component").each(function() {
		var component = $(this);
		var title = $(this).text();
		$(this).text("")
		var span = $("<span class='yaezakura-component-text'>" + title + "</span>")
		component.append(span);
		component.on('click', function() {
			if (component.hasClass('yaezakura-component-checked')) {
				component.removeClass("yaezakura-component-checked")
			} else {
				component.addClass("yaezakura-component-checked")
			}
		})
	})

	$(".yaezakura-component-text").each(function() {
		var span = $(this);
		var spanH = span.height();
		var top = (100 - spanH) / 2
		span.css("top", top)
	})
})

function getTime() {
	var datetime = new Date();
	//datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
	var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	//return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
	return year + month + data + hour + minute + second
}

function getDate() {
	var datetime = new Date();
	//datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	return year + "-" + month + "-" + date;
}

var chars = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' ];

function generateMixed(n) {
	var res = "";
	for (var i = 0; i < n; i++) {
		var id = Math.ceil(Math.random() * 35);
		res += chars[id];
	}
	return res;
}
/*组件展示成徽标样式，好看一点，以button实现*/
function layLabel(names) {
	var result = "";
	for (var i = 0; i < names.length; i++) {
		var text = names[i]
		result = result + "<button class='layui-btn layui-btn-mini layui-btn-radius layui-btn-primary'>" + text + "</button>"
	}
	return result;
}

function getRandom() {
	var colors = [ "", "layui-btn-primary", "layui-btn-normal", "layui-btn-warm", "layui-btn-danger" ];
	var index = Math.floor((Math.random() * colors.length));
	return colors[index];
}

/*加载第一页的组件*/
function initComponentType(column) {
	var container = $("#yaezakura-component-container");
	var lines = componentData.length / column + 1
	for (var i = 1; i < componentData.length; i++) {
		var value = componentData[i].value;
		var text = componentData[i].text;
		var component = $('<div class="yaezakura-component" name="' + value + '">' + text + '</div>')
		if ((i - 1) % column == 0) {
			var formItem = $('<div class="layui-form-item" align="center"></div>')
			container.append(formItem)
		}
		formItem.append(component)
	}
	//$("#yaezakura-component-container div.layui-form-item").last().attr("align","left")

/*	
	for (var i = 0; i < lines; i++) {
		var formItem = $('<div class="layui-form-item" align="center"></div>')
		for (var j = 0; j < column; j++) {
			var index = (i + 1) * (j + 1);
			var value = componentData[index].value;
			var text = componentData[index].text;
			var component = $('<div class="yaezakura-component" name="' + value + '">' + text + '</div>')
			formItem.append(component);
		}
		container.append(formItem)
	}*/
}


function setIframeHeight(iframe) {
	if (iframe) {
		var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
		if (iframeWin.document.body) {
			iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
		}
	}
}

function validReturnData(data){
	var data = JSON.parse(data);
	if(data.code == -1){
		layer.msg(data.message, {icon: 5});
		return false;
	}
	return true;
}

/*自定义列表ligerCombox方法*/
$.fn.layCombox = function(options) {
	this.empty()
	var data = options.data
	var selected = options.selected;
	for (var i = 0; i < data.length; i++) {
		var singleOption = $('<option value="' + data[i].value + '">' + data[i].text + '</>')
		if (data[i].value == selected) {
			singleOption.attr("selected", "selected");
		}
		this.append(singleOption)
	}

}

/*自定义验证规则*/
layui.use('form', function() {
	var form = layui.form();
	form.verify({
		ipaddress : function(value, item) { //value：表单的值、item：表单的DOM对象
			var reg = /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
			if (!reg.test(ip)) {
				return "请输入正确的IP地址";
			}
		}, //我们既支持上述函数式的方式，也支持下述数组的形式 //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
		ip : [
			/^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/
			, '请输入正确的IP地址'
		],
		port : [ /^([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-5]{2}[0-3][0-5])$/, '请输入正确的端口' ]
	});
});



/*dataTables的常量*/
var CONSTANT = {
	DATA_TABLES : {
		DEFAULT_OPTION : { // DataTables初始化选项  
			CREATE_ROW : function(row, data, dataIndex) {
				$(row).attr('id', "row-" + data.id);
				var checkbox = $(row).find("input[type='checkbox']")

				$(row).children(":not(:first)").each(function() {
					$(this).click(function() {
						if (checkbox.prop("checked")) {
							checkbox.prop('checked', false);
						} else {
							checkbox.prop("checked", true);
						}
					})
				})

				$(row).click(function() {
					var checkbox = $(this).find("input[type='checkbox']")
					if (checkbox.prop("checked")) {
						$(this).addClass('select');
					} else {
						$(this).removeClass('select');
					}
				})
			},

			LANGUAGE : {
				processing : "处理中...",
				sLengthMenu : "显示 _MENU_ 项结果",
				sZeroRecords : "没有匹配结果",
				sInfo : "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
				sInfoEmpty : "显示第 0 至 0 项结果，共 0 项",
				sInfoFiltered : "(由 _MAX_ 项结果过滤)",
				sInfoPostFix : "",
				sSearch : "搜索:",
				sUrl : "",
				sEmptyTable : "没有数据",
				sLoadingRecords : "载入中...",
				sInfoThousands : ",",
				oPaginate : {
					sFirst : "首页",
					sPrevious : "上页",
					sNext : "下页",
					sLast : "末页"
				}
			},
			//禁用用户自己定义每页数量
			lengthChange : false,
			// 禁用自动调整列宽  
			autoWidth : true,
			// 为奇偶行加上样式，兼容不支持CSS伪类的场合  
			//stripeClasses : [ "odd", "even" ],
			// 取消默认排序查询,否则复选框一列会出现小箭头  
			order : [],
			// 隐藏加载提示,自行处理  
			processing : true,
			// 启用服务器端分页  
			serverSide : true,
			// 禁用原生搜索  
			searching : false
		},
		COLUMN : {
			// 复选框单元格  
			CHECKBOX : {
				width : "40px",
				className : "td-checkbox",
				orderable : false,
				data : "id",
				render : function(data, type, row, meta) {
					var content = '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">';
					content += '    <input type="checkbox" class="group-checkable" value="' + data + '" />';
					content += '    <span></span>';
					content += '</label>';
					//var content = '<input name="layTableCheckbox" lay-skin="primary" checked="" type="checkbox">'
					return content;
				}
			}
		},
		// 常用render可以抽取出来，如日期时间、头像等  
		RENDER : {
			ELLIPSIS : function(data, type, row, meta) {
				data = data || "";
				return '<span title="' + data + '">' + data + '</span>';
			},
			TRIMRENDER : function(data, type, row, meta) {
				if (data.length > 50) {
					return data.substring(0, 46) + "......"
				} else {
					return data
				}
			}
		},

		/*如果一个字段字符太多，可以用此方法来显示前50个*/

	}
};