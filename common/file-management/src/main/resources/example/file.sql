CREATE TABLE t_file (
    id UUID NOT NULL,
   file_name VARCHAR(255) NOT NULL,
   content_type VARCHAR(255),
   size INTEGER,
   storage_directory_name VARCHAR(255),
   storage_file_name VARCHAR(255),
   CONSTRAINT pk_t_file PRIMARY KEY (id)
);