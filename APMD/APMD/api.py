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

    print(res,"//////////////////////////////")



    if res:
    
        data['status']="success"
        # data['method']="login"
        data['data']=res
        
    else:
        data['status']="failed"
        # data['method']="login"
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
	q="insert into complaints values(NULL,'%s','%s','%s','pending',curdate())"%(loginid,complaint,des)
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
	q="select * from complaints where sender_id='%s'"%(loginid)
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



@api.route("/user_view_test_results")
def user_view_test_results():
	data={}
	id=request.args['loginid']
	s="select * from students where login_id='%s'"%(id)
	res1=select(s)
	lid=res1[0]['student_id']

	qry="select * from answers where student_id='%s'"%(lid)
	res=select(qry)
	if res:
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	return str(data)

from sample import compare_phonetic_similarity
# @api.route('/textspeech', methods=['GET', 'POST'])
# def textspeech():
# 	data = {}
# 	tv_result = request.args.get('tvResult')  # Retrieve the 'tvResult' query parameter
# 	qn_id = request.args['qn_id']
# 	id=request.args['log_id']
# 	qry="select * from questions where Question_id='%s'"%(qn_id)
# 	res=select(qry)
# 	print(tv_result, '///////////////////////////////////////////////////')
# 	phonetic_similarity = compare_phonetic_similarity(res[0]['Answer'], tv_result)
# 	print(phonetic_similarity,'******************')
# 	if phonetic_similarity == True:
# 		qry1="insert into answers values(null,'%s',(select student_id from students where login_id='%s'),'%s','1',curdate())"%(qn_id,id,tv_result)
# 		insert(qry1)
# 		data['status']='success'
# 		data['out']='Correct Answer'
# 	else:
# 		qry1="insert into answers values(null,'%s',(select student_id from students where login_id='%s'),'%s','0',curdate())"%(qn_id,id,tv_result)
# 		insert(qry1)
# 		data['status']='success'
# 		data['out']='Incorrect Answer'
# 	return str(data)





import os
import uuid
import speech_recognition as sr
from flask import request

from pydub import AudioSegment

AudioSegment.converter = "C://PATH_programs//ffmpeg.exe"


@api.route('/audioo', methods=['POST'])
def video():
    data = {}
    if 'audio' not in request.files:
        return 'No file part'

    audio_file = request.files['audio']
    qn_id = request.form['question_id']
    print(qn_id,"////question no")
    id = request.form['login_id']
    print(id,"login_id/////////////////")
    

    if audio_file.filename == '':
        return 'No selected file'

    # Generate a unique filename
    unique_filename = str(uuid.uuid4()) + ".mp3"
    

    # Save the file to a folder with the generated unique filename
    upload_folder = 'C://Users//Ponnu//Desktop//APMD_mes_delivery//APMD//APMD//static'
    
    # upload_folder = 'audio'
    
    audio_path = os.path.join(upload_folder, unique_filename)
    audio_file.save(audio_path)

    print("Audio file saved:", audio_path)

    # Convert audio file to WAV format
    wav_path = os.path.splitext(audio_path)[0] + '.wav'
    try:
        sound = AudioSegment.from_file(audio_path)
        sound.export(wav_path, format="wav")
    except Exception as e:
        return f"Error converting audio: {str(e)}"

    print("Audio converted to WAV:", wav_path)

    # Initialize the recognizer
    recognizer = sr.Recognizer()

    # Load audio file
    with sr.AudioFile(wav_path) as source:
        audio_data = recognizer.record(source)

    # Convert audio to text
    try:
        # Recognize the audio
        result = recognizer.recognize_google(audio_data, show_all=True)
        print("Recognition result:", result)

        # Extract the first transcript from the alternatives, if available
        if 'alternative' in result:
            first_transcript = result['alternative'][0]['transcript']
        else:
            first_transcript = "No alternative transcripts available"

    except sr.UnknownValueError:
        first_transcript = "Could not understand audio"
    except sr.RequestError as e:
        first_transcript = f"Could not request results; {e}"

    print("First transcript:", first_transcript)

    # Clean up: delete audio files

    os.remove(wav_path)

    qry = "select * from questions where Question_id='%s'" % (qn_id)
    res = select(qry)
    print(first_transcript, '///////////////////////////////////////////////////')
    phonetic_similarity = compare_phonetic_similarity(res[0]['Answer'], first_transcript)
    print(phonetic_similarity,"/////////////////////////hdwqhdbhudih")
    if phonetic_similarity == True:
        qry1 = "insert into answers values(null,'%s',(select student_id from students where login_id='%s'),'%s','1',curdate(),'%s')" % (
        qn_id, id, first_transcript, unique_filename)
        insert(qry1)
        
        data={"status":"success","text":first_transcript,"out" : "Correct Answer"}

    else:
        qry1 = "insert into answers values(null,'%s',(select student_id from students where login_id='%s'),'%s','0',curdate(),'%s')" % (
        qn_id, id, first_transcript, unique_filename)
        insert(qry1)
        data={"status":"success","text":first_transcript,"out" : "Incorrect Answer"}

    print("Response data:", data)

    return jsonify(data)


@api.route("/user_view_anser")
def user_view_anser():
    data={}
    id=request.args['id']
    qry="select * from questions inner join answers using(Answer_id) where question_id='%s'"%(id)
    res=select(qry)
    print(res,"////////////")
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
    data['output']='view'
    return str(data)



    







