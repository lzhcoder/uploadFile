访问：http://localhost/uploadFile/

情况一：先申请ID,后上传文件刚必须要携带两个参数以下的 id (之前申请的 )， origin （来源标示默认为A0，选填） 
id:         *申请的ID 
origin:     *origin来源编号（A1:终端，A2：SWING，A3:WEB） 
replace:    *replace=1 为替换上传 
note:       *备注 
File:      选择文件  上传


情况二：直接上传只须携带 origin （来源标示默认为A0，选填）， 当上传成功会返回 id 和 vid 
origin:     *origin来源编号（A1:终端，A2：SWING，A3:WEB） 
File:  选择文件  上传
申请ID地址示例： 
请求一个ID方式：  
http://localhost/uploadFile/getIds
  测试 
请求多个ID方式：  
http://localhost/uploadFile/getIds?count=5
  测试 (count 不能超过10 ) 
访问方式示例： 
第一种：  
http://localhost/uploadFile/get?id=123
 
第二种：  
http://localhost/uploadFile/get?vid=23A8EACE06AD5F4A4A1B933E0063E100BF652A830A57C918852E069384E2673C 
 
第三种：  
http://localhost/uploadFile/upload/A0/A1/2013/5/100251.jpg
 
(注意: 第三种方式必须把文件的保存路径配置到web_path/upload/， WebRoot/conf/upload.properties配置文件中配置。) 
下载方式示例： 
第一种：  
http://localhost/uploadFile/download?id=123 
 
第二种：  
http://localhost/uploadFile/download?vid=23A8EACE06AD5F4A4A1B933E0063E100BF652A830A57C918852E069384E2673C 



一般来说，ajax不能发送文件（新规范中有了支持，但低版本浏览器中肯定不行），所以文件ajax上传的原理一般不用ajax实现，用表单提交。

 一般的表单提交会刷新页面，所谓的ajax上传就无从谈起，所以先说说无刷新的表单提交

    <form target="frm" action="xxx.htm">  
    <button type="submit">提交</button>  
    </form>  
    <iframe id='frm' name='frm'></iframe>  

    提交如上表单，服务端返回的内容将渲染到上面的iframe，主页面将不刷新。

    基于该机制，就可以实现无刷新文件上传
    
        <form target="frm" action="Default.aspx?type=upFile" method="post" enctype="multipart/form-data">  
    <input type='file' name="UploadFile" />   
    <button type="submit">提交</button>  
    </form>  
    <iframe id='frm' name='frm'></iframe>  
      
    如上表单，点击上传，即可成功上传文件而不刷新页面  
      
    当然作为一个类ajax接口，仅仅无刷新还不够，我们还需要实现回调函数，并获得服务端返回值  
      
    这需js实现,为简化代码，使用jQuery代码  
    <script>  
        function callback(res){  
            alert(res);  
        }  
        var frm = $("#frm");  
        frm.load(function(){  
            var wnd = this.contentWindow;  
            var str = $(wnd.document.body).html();  
            callback(str);  
        });  
          
    </script>  
      
    就这样，上传完成后就会执行callback函数，并传递服务端返回值。  
    按照该原理，可以轻松包装出一个ajax文件上传组件。  
    
  REST(Representational State Transfer)  based Web Service是相对于传统的Web Service(SOAP+WSDL+UDDI)而提出的。
   传统的Web Service可以很好的解决异构系统之间的通信问题，但是需要首先定义好XML格式的合同(WSDL)，
  client和server都必须严格遵守协议，不容易升级以及集群伸缩。REST Web Service不需要事先定义格式，
   传输的内容也可以依据不同的client变化(json,xml,html等)， 最重要的是使用源URL来唯一定位资源，对资源的增删改查映射为HTTP的四个方法，
   无状态传输，具有非常好的伸缩性。  
    
    输入http://localhost/uploadFile/rest/即可看到web service列表信息 
 