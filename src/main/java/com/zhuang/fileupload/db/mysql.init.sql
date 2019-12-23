delete from sys_fileupload_template;

INSERT sys_fileupload_template(id,name,description,save_dir,status,create_time)
  VALUE('sys_user','用户头像','用户头像','sys_user',1,now());
