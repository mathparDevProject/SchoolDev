#SELECT count(*)
#    INTO @exists
#FROM institution
#WHERE id=1;
#
#SET @query = If(@exists=0,
#                'insert into institution (id, accreditation_level, address, institution_type, name) values (1, 1, \'address\', 0, \'name\');',
#                'select 1');
#SET @query2 = If(@exists=0,
#                'insert into userProfiles(id, email, name, password, role, institution_id) values (1, \'email\', \'name\', \'$2a$10$gdwKHXbd/Jxn4CV8yiaWpOMBZeygy/flGewwsUQ1d8p6Xyh8z32Pe\', 0, 1);',
#                'select 1');
#
#PREPARE stmt FROM @query;
#PREPARE stmt2 FROM @query2;
#
#EXECUTE stmt;
#EXECUTE stmt2;

SELECT 1;