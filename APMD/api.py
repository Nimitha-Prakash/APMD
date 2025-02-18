from flask import *
from database import *
import demjson

import uuid

from sample import recognize_speech


api=Blueprint("api",__name__)




@api.route('/loginapi',methods=['get','post'])
def loginapi():
	data={}
	username=request.args['username']
	password=request.args['password']
	
	q="select * from login where username='%s' and password='%s'" %(username,password)
	res=select(q)


	if res:
	
		data['status']="success"
		data['method']="login"
		data['data']=res
		
	else:
		data['status']="failed"
		data['method']="login"
	# return demjson.encode(data)
	return str(data)



@api.route('/registerstud',methods=['get','post'])
def registerstud():
	data={}
	first_name=request.args['fname']
	last_name=request.args['lname']
	# house_name=request.args['hname']
	place=request.args['place']
	# pincode=request.args['pin']
	phone=request.args['phone']
	email=request.args['email']
	username=request.args['uname']
	password=request.args['pass']
	q="insert into login values(NULL,'%s','%s','student')"%(username,password)
	id=insert(q)
	q="insert into students values (NULL,'%s','%s','%s','%s','%s')"%(id,first_name,last_name,phone,email)
	iid=insert(q)
	if iid:
		data['status']="success"
		data['method']="register"
		
	else:
		data['status']="failed"
		data['method']="register"
	return str(data)



@api.route("/User_profile")
def User_profile():
	data={}
	lid=request.args['log_id']
	q="select * from students where login_id='%s'"%(lid)
	print(q)
	res=select(q)

	data['status']='success'
	data['method']='User_feedbak'
	data['data']=res
	print("#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",res)

	return str(data)


@api.route('/profile_update')
def profile_update():
    print("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
    data={} 
	
    fname=request.args['fname']
    lname=request.args['lname']
    # place=request.args['place']
    phone=request.args['phone']
    email=request.args['email']
    lid=request.args['log_id']
    print("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&",fname)
    u="update students set fname='%s',lname='%s',phone='%s',email='%s' where login_id='%s'"%(fname,lname,phone,email,lid)
    up=update(u)
    if up:
        data['status']="success"
    else:
        data['status']='failed'

    data['status']="success"
   
    
    return str(data)


@api.route('/usersendcomplaint',methods=['get','post'])
def usersendcomplaint():
	data={}
	loginid=request.args['loginid']
	complaint=request.args['complaint']
	des=request.args['des']
	q="select * from students where login_id='%s'"%(loginid)
	res1=select(q)
	q="insert into complaints values(NULL,'%s','%s','%s','pending',curdate())"%(res1[0]['student_id'],complaint,des)
	id=insert(q)
	if id:
		data['status']="success"
		data['method']="usersendcomplaint"
		data['data']=id
		
	else:
		data['status']="failed"
		data['method']="usersendcomplaint"
	return str(data)



@api.route('/userviewcomplaints',methods=['get','post'])
def userviewcomplaints():
	data={}
	loginid=request.args['loginid']
	q="select * from students where login_id='%s'"%(loginid)
	res1=select(q)
	q="select * from complaints where sender_id='%s'"%(res1[0]['student_id'])
	res=select(q)
	if res:
		data['status']="success"
		data['method']="userviewcomplaint"
		data['data']=res
		
	else:
		data['status']="failed"
		data['method']="userviewcomplaint"
	return str(data)


@api.route('/process_audio', methods=['POST'])
def process_audio():
    audio_data = request.files['audio_data']
    
    # Save the audio data to a file or process it directly
    # Example: Save the audio data to a file
    audio_data.save("received_audio.wav")
    
    # Process the audio data using the provided functions
    recorded_text = recognize_speech("received_audio.wav")
    
    # Further processing based on the recorded text
    return {"recorded_text": recorded_text}



@api.route("/user_view_test")
def user_view_test():
    data={}
    qry="select * from test"
    res=select(qry)
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    return str(data)


@api.route("/user_view_question")
def user_view_question():
    data={}
    id=request.args['id']
    qry="select * from questions where Test_id='%s'"%(id)
    res=select(qry)
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    return str(data)


from sample import compare_phonetic_similarity
@api.route('/textspeech', methods=['GET', 'POST'])
def textspeech():
	data = {}
	tv_result = request.args.get('tvResult')  # Retrieve the 'tvResult' query parameter
	qn_id = request.args['qn_id']
	id=request.args['log_id']
	qry="select * from questions where Question_id='%s'"%(qn_id)
	res=select(qry)
	print(tv_result, '///////////////////////////////////////////////////')
	phonetic_similarity = compare_phonetic_similarity(res[0]['Answer'], tv_result)
	print(phonetic_similarity,'******************')
	if phonetic_similarity == True:
		qry1="insert into answers values(null,'%s',(select student_id from students where login_id='%s'),'%s','1')"%(qn_id,id,tv_result)
		insert(qry1)
		data['status']='success'
		data['out']='Correct Answer'
	else:
		qry1="insert into answers values(null,'%s',(select student_id from students where login_id='%s'),'%s','0')"%(qn_id,id,tv_result)
		insert(qry1)
		data['status']='success'
		data['out']='Incorrect Answer'
	return str(data)