COPY jobs(id, job_name, job_desc) FROM 'C:\Users\Public\Documents\jobs.csv' WITH CSV HEADER;
COPY volunteers(id, first_name, last_name) FROM 'C:\Users\Public\Documents\volunteers.csv' WITH CSV HEADER;
COPY jobs_volunteers(job_id, volunteer_id) FROM 'C:\Users\Public\Documents\jobs_volunteers.csv' WITH CSV HEADER;