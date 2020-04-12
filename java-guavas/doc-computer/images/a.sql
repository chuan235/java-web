CREATE TABLE `test_rows` (
  `id` int(11) NOT NULL auto_increment,
  `num` int(11) NOT NULL default '0',
	`des` varchar(32) DEFAULT NULL,
	`remark` varchar(32) DEFAULT NULL,
	`create_time` datetime DEFAULT NULL,
  PRIMARY KEY  (`id`)
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;



create procedure rows_test(pa int(11))
begin
 declare i int default 0;
 declare rand_num int;
 DECLARE rand_str1 VARCHAR(32) DEFAULT '';
 DECLARE rand_str2 VARCHAR(32) DEFAULT '';
 DECLARE rand_time VARCHAR(32) DEFAULT '';
 while i < pa do
     select cast(rand()*100 as unsigned) into rand_num;
     select CONCAT(FLOOR(2000 + (RAND() * 16)),'-',LPAD(FLOOR(1 + (RAND() * 12)),2,0),'-',LPAD(FLOOR(3 + (RAND() * 8)),2,0), ' ',LPAD(FLOOR(0 + (RAND() * 23)),2,0),':',LPAD(FLOOR(0 + (RAND() * 59)),2,0),':',LPAD(FLOOR(0 + (RAND() * 59)),2,0)) INTO rand_time;
     set rand_str1 = substring(MD5(RAND()),1,20);
     set rand_str2 = substring(MD5(RAND()),1,20);
     insert into test_rows(num,des,remark,create_time) values(rand_num,rand_str1,rand_str2, STR_TO_DATE(rand_time,'%Y-%m-%d %H:%i:%s'));
   set i = i +1;
 end while;
end

call rows_test(500000)









