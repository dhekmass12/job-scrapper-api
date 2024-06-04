from flask import Flask, render_template, request
import psycopg2
import psycopg2.sql

app = Flask(__name__)

def get_all_jobs():
    conn = psycopg2.connect('postgres://avnadmin:AVNS_4bs3MyIAyVcRfFZ3shQ@iap-online-mysql.c.aivencloud.com:10372/defaultdb?sslmode=require')
    cur = conn.cursor()
    cur.execute('SELECT VERSION()')
    version = cur.fetchone()[0]
    print(version)
    GET_ALL_JOBS_COMMAND = '''
        SELECT * FROM job
    '''
    try:
        cur.execute(GET_ALL_JOBS_COMMAND)
        result = cur.fetchall()
        print("jobs: ", result)
        return result
    except (psycopg2.DatabaseError, Exception) as error:
        print(error)
        
def get_filtered_jobs(jobs, job_fields, published_dates, locations, companies):
    new_jobs = []
    for job in jobs:
        if ((companies == [''] or job[2].lower() in companies) and 
            (locations  == [''] or job[3].lower() in locations) and
            (published_dates == [''] or job[6].lower() in published_dates) and
            (job_fields == [''] or job[7].lower() in job_fields)):
            job = list(job)
            new_jobs.append(job)
            
    return new_jobs
    

@app.get('/jobs')
def search():
    job_fields = request.args['job_fields']
    job_fields = job_fields.split(",")
    for i in range(len(job_fields)):
        job_fields[i] = job_fields[i].lower()
    published_dates = request.args['published_dates']
    published_dates = published_dates.split(",")
    for published_date in published_dates:
        if published_date != "" and len(published_date) != 10:
            raise Exception("ERROR: TANGGAL HARUS TERDIRI DARI 10 KARAKTER")
    locations = request.args['locations']
    locations = locations.split(",")
    for i in range(len(locations)):
        locations[i] = locations[i].lower()
    companies = request.args['companies']
    companies = companies.split(",")
    for i in range(len(companies)):
        companies[i] = companies[i].lower()
    
    jobs = get_all_jobs()
    jobs = get_filtered_jobs(jobs, job_fields, published_dates, locations, companies)
    
    return jobs, 200