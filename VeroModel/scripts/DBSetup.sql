connect 'jdbc:derby:vero;create=true;user=verometadata';
call syscs_util.syscs_set_database_property('derby.connection.requireAuthentication', 'true');
call syscs_util.syscs_set_database_property('derby.user.verometadata', 'verometadata');
run 'CreateVeroMetadataDBObjects.sql';