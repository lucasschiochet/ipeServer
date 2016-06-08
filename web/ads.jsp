<!DOCTYPE html>
<style>

body{
  font-family: "Myriad Pro", Helvetica, Arial, sans-serif;
}
.square{
  width: 98%;
  height: 0;
  padding-bottom: 70%;
  margin: 1%;
  position: relative;
}

.block{
position: absolute;
text-align: center;
background: white;
width: 100%;
height: 100%;
}

.block:before {
content: '';
display: inline-block;
height: 100%;
vertical-align: middle;
margin-right: -0.25em;
}

.centered {
border: 2px solid;
border-color: #256bb7;
display: inline-block;
vertical-align: middle;
width: 80%;
height: 80%;
background: white;
color: black;
}

</style>
<html>
  <body>
    <center>
      <div class="square">
          <div class="block">

            <div class="centered">
                <h1 style="margin-top: 20%; font-size: 60px">Internet gr&aacute;tis</h1>
                <div  onclick="startRequest()"><img id="imgUserMe" src="botao.svg" style="width: 40%;"></img></div>
                <center><table id="tblLoading" style="display:none;"><tr><td><img style="width:32px;height:32px;"src="spinner.gif"/></td><td style="padding-left:10px;font-size: 34px;">Carregando</td></tr></table></center>
                <p style="font-size: 13px; margin-top: 1%">Clicando no bot&atilde;o acima, voc&ecirc; concorda com nossos <u>Termos de Uso</u>.</p>
            </div>
        </div>
      </div>
  </center>
  </body>

 <script src="http://ipe.nday.com.br:8383/ipe/jquery.min.js"></script>
 <script>
        var loading = false;
        
        function startLoadingUI(){
            document.getElementById("imgUserMe").style.display = "none";
            document.getElementById("tblLoading").style.display = "inline";
        }
    
        function getURLParameters(paramName){
            var sURL = window.document.URL.toString();
            if (sURL.indexOf("?") > 0){
               var arrParams = sURL.split("?");
               var arrURLParams = arrParams[1].split("&");
               var arrParamNames = new Array(arrURLParams.length);
               var arrParamValues = new Array(arrURLParams.length);
               var i = 0;
               for (i = 0; i<arrURLParams.length; i++){
                   var sParam =  arrURLParams[i].split("=");
                   arrParamNames[i] = sParam[0];
                   if (sParam[1] != "")
                       arrParamValues[i] = unescape(sParam[1]);
                   else
                       arrParamValues[i] = "No Value";
               }

               for (i = 0; i < arrURLParams.length; i++) {
                   if (arrParamNames[i] == paramName) {
                        return arrParamValues[i];
                      } 
                   }
                }

                return "";
            }
            
            function requestAccess(accessToken, vlanIp){
 
                if(loading == true){
                    return;
                }else{
                    loading = true;
                    startLoadingUI();
                }
		            $.ajax({
                   type: "POST",
                   url: "http://104.131.85.120:8383/ipe/webresources/deviceOperation/requestAccess",
                   data:{'accessToken':accessToken,
                         'vlanIp':vlanIp},
                   beforeSend: function(xhr ){}, 
                   success: function (data) {
                      if(data=="OK"){
                          setTimeout(function(){
                             parent.location.href = "http://104.131.85.120:8383/ipe/index.html";
                           }, 1000);
                   	   
                      }else{
                           alert(data); 
                      }
                   }
               });
	          }
            
            function startRequest(){
                var accessToken = getURLParameters("accessToken");
                var vlanIp = getURLParameters("vlanIp");
                requestAccess(accessToken, vlanIp);
            }
            
            setTimeout(function(){
                startRequest();
            }, 7000);
    </script>
</html>