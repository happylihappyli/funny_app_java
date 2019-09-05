

var ip="robot5.funnyai.com";
var user="root";
var private_file="D:\\Net\\Web\\id_rsa.pkcs8";
var strCode=s_file.read("D:\\Net\\Web\\password_upload_1.txt");
var password=s_rsa.decrypt_by_private(private_file,strCode);

var command="cd happyli\nls\npwd\n";
s_net.ssh(ip,user,password,"",5,command);


