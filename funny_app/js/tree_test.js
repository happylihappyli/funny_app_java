

var str=s_tree.train("D:\\Funny\\Data\\weather.arff",3);
s_out.println(str);

var html=s_file.read("@\\..\\data\\viz.html.template");
html=html.replace("{sys.data.0}",str);

var file="@\\..\\test\\test.html";
s_file.save(file,html);

s_sys.exit(0);