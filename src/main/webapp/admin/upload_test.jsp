<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Test Upload</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<style type="text/css">
div {
	border: 1px solid #CCC;
	margin: 10px 0px;
}
</style>
        <script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript">
	var pName = '/uploadFile';
	var pUrl = document.location.protocol + '//' + document.location.host
			+ pName;
	function visit(s) {
		var url = $(s).val();
		$('#urls').empty();
		$('#urls').html(url);
		window.frames['con'].location.href = url;

	}
	$(function() {
		$('#getIds').val(pUrl + '/getIds');
		$('#getIds2').val(pUrl + '/getIds?count=5');
		$('#getF').val(pUrl + '/get?id=123');
		$('#getF2')
				.val(
						pUrl + '/get?vid=23A8EACE06AD5F4A4A1B933E0063E100BF652A830A57C918852E069384E2673C ');
		$('#getF3').val(pUrl + '/upload/A0/A1/2013/5/100251.jpg');
		$('#download').val(pUrl + '/download?id=123 ');
		$('#download2')
				.val(
						pUrl + '/download?vid=23A8EACE06AD5F4A4A1B933E0063E100BF652A830A57C918852E069384E2673C ');
	});
</script>
	</head>
	<body>
		提交上传地址:http://ip:port/uploadFile/upload
		<div>
			情况一：先申请ID,后上传文件刚必须要携带两个参数以下的 id (之前申请的 )， origin （来源标示默认为A0，选填）
			<br />
			<form name="myform" action="../upload" method="post"
				enctype="multipart/form-data">
				id:
				<input type="text" name="id" id="id">
				*申请的ID
				<br />
				origin:
				<input type="text" name="origin" id="origin">
				*origin来源编号（A1:终端，A2：SWING，A3:WEB）
				<br />
				replace:
				<input type="text" name="replace" id="replace">
				*replace=1 为替换上传
				<br />
				note:
				<input type="text" name="note" id="note">
				*备注
				<br />
				File:
				<input type="file" name="myfile">
				<input type="submit" name="submit" value="上传">
			</form>
		</div>
		<div>
			情况二：直接上传只须携带 origin （来源标示默认为A0，选填）， 当上传成功会返回 id 和 vid
			<br />
			<form name="myform2" action="../upload" method="post"
				enctype="multipart/form-data">
				origin:
				<input type="text" name="origin" id="origin">
				*origin来源编号（A1:终端，A2：SWING，A3:WEB）
				<br />
				File:
				<input type="hidden" name="userName" value="lzh">
				<input type="file" name="myfile">
				<input type="submit" name="submit" value="上传">
			</form>

		</div>
		<div>
			申请ID地址示例：
			<br />
			请求一个ID方式：
			<input type="text" name="getIds" id="getIds" size="128">
			<input type="button" value="测试" onclick="visit('#getIds');" />
			<br />
			请求多个ID方式：
			<input type="text" name="getIds2" id="getIds2" size="128">
			<input type="button" value="测试" onclick="visit('#getIds2');" />
			(count 不能超过10 )
			<br />
		</div>
		<div>
			访问方式示例：
			<br />

			第一种：
			<input type="text" name="getF" id="getF" size="128">
			<input type="button" value="测试" onclick="visit('#getF');" />
			<br />
			第二种：
			<input type="text" name="getF2" id="getF2" size="128">
			<input type="button" value="测试" onclick="visit('#getF2');" />
			<br />
			第三种：
			<input type="text" name="getF3" id="getF3" size="128">
			<input type="button" value="测试" onclick="visit('#getF3');" />
			<br />
			(注意: 第三种方式必须把文件的保存路径配置到web_path/upload/，
			WebRoot/conf/upload.properties配置文件中配置。)
			<br />
		</div>
		<div>
			下载方式示例：
			<br />
			第一种：
			<input type="text" name="download" id="download" size="128">
			<input type="button" value="测试" onclick="visit('#download');" />
			<br />
			第二种：
			<input type="text" name="download2" id="download2" size="128">
			<input type="button" value="测试" onclick="visit('#download2');" />
			<br />
		</div>
		<div>
			iframe url:
			<span id="urls"></span>
			<iframe id="con" name="con" frameborder="1" scrolling="auto"
				width="100%" height="100%"></iframe>
		</div>
	</body>
</html>
