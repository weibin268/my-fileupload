
DROP TABLE IF EXISTS sys_fileupload;
CREATE TABLE sys_fileupload
(
    id VARCHAR(50) PRIMARY KEY,
    template_id VARCHAR(50) NOT NULL,
    biz_id VARCHAR(50) NOT NULL,
    save_full_path VARCHAR(1000) NOT NULL,
    origin_file_name VARCHAR(500),
  	status       INT,
  	create_time  DATETIME,
  	modify_time DATETIME,
  	create_by    VARCHAR(50),
  	modify_by   VARCHAR(50)
);
ALTER TABLE sys_fileupload COMMENT = '文件上传记录表';
ALTER TABLE sys_fileupload ADD INDEX IDX_created_time(create_time);



DROP TABLE IF EXISTS `sys_fileupload_template`;

CREATE TABLE sys_fileupload_template
(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    save_dir VARCHAR(500) NOT NULL,
    status       INT,
    create_time  DATETIME,
    modify_time DATETIME,
    create_by    VARCHAR(50),
    modify_by   VARCHAR(50)
);
ALTER TABLE sys_fileupload_template COMMENT = '文件上传模板表';
ALTER TABLE sys_fileupload_template ADD INDEX IDX_created_time(create_time);
