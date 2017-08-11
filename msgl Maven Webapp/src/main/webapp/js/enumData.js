var emptyData = {
	value : "",
	text : "---请选择---"
};

var nodeTypeData = [
emptyData,
{
	value : 1,
	text : "管理节点"
}, {
	value : 2,
	text : "数据节点"
}             
                    
]

var componentData = [
	emptyData,
	{
		value : 1,
		text : "Yarn&MR"
	},
	{
		value : 2,
		text : "Spark"
	},
	{
		value : 3,
		text : "Storm"
	},
	{
		value : 4,
		text : "Zookeeper"
	},
	{
		value : 5,
		text : "Hbase"
	},
	{
		value : 6,
		text : "Hive"
	},
	{
		value : 7,
		text : "HDFS"
	},
	{
		value : 8,
		text : "Impala"
	},
	{
		value : 9,
		text : "Kafka"
	},
	{
		value : 10,
		text : "Flume"
	},{
		value : 11,
		text : "MPP"
	}
]

var classificationData = [
	emptyData,
	{
		value : "主备管理",
		text : "主备管理"
	},
	{
		value : "传输加密",
		text : "传输加密"
	},
	{
		value : "多租户安全",
		text : "多租户安全"
	},
	{
		value : "数据加密",
		text : "数据加密"
	},
	{
		value : "日志审计",
		text : "日志审计"
	},
	{
		value : "版本管理",
		text : "版本管理"
	},
	{
		value : "访问控制",
		text : "访问控制"
	},
	{
		value : "身份认证",
		text : "身份认证"
	},
	{
		value : "集群管理",
		text : "集群管理"
	},{
		value : "安全能力与保障",
		text : "安全能力与保障"
	}, {
		value : "可用性",
		text : "可用性"
	}
]

var appTypeData = [
	emptyData,
	{
		value : 1,
		text : "大数据存储组件"
	},
	{
		value : 2,
		text : "大数据应用组件"
	},
	{
		value : 3,
		text : "大数据采集组件"
	},
]

var experienceTypeData = [
	emptyData,
	{
		value : 1,
		text : "开发"
	},
	{
		value : 2,
		text : "测试"
	},
	{
		value : 3,
		text : "系统"
	},
]


function getTextByValue(value, data) {
//	value = String(value);
	for (var i = 0; i < data.length; i++) {
		if (value == data[i].value) {
			return data[i].text;
		}
	}
}

function getValueByText(text, data) {
//	text = String(text);
	for (var i = 0; i < data.length; i++) {
		if (text == data[i].text) {
			return data[i].value;
		}
	}
}