from flask import*
from database import*

app=Flask(__name__)
public=Blueprint('public',__name__)

@public.route("/")
def home():
    return render_template("home.html")

@public.route("/login",methods=['get','post'])
def login():
    if 'reg' in request.form:
        uname=request.form['uname']
        password=request.form['pass']
        qry1="select * from login where username='%s' and password='%s'"%(uname,password)
        res=select(qry1)
        session['log']=res[0]['login_id']
        if res:
            user_type=res[0]['user_type']
            
        if res[0]['user_type']=='admin':
            return redirect(url_for('admin.admin_home'))
        if res[0]['user_type']=='teacher':
            qry="select * from teacher where login_id='%s'"%(session['log'])
            res1=select(qry)
            session['teacher']=res1[0]['Teacher_id']
            return redirect(url_for('teacher.teacher_home'))
        if res[0]['user_type']=='student':
            return redirect(url_for('student.student_home'))
        
    return render_template("login.html")
@public.route("/register",methods=['get','post'])
def reg():
    data={}
    return render_template("register.html",data=data)

@public.route("/teacher_reg",methods=['get','post'])
def teacher_reg():
    if 'submit' in request.form:
        fname=request.form['fname']
        lname=request.form['lname']
        phone=request.form['phone']
        email=request.form['email']
        qualification=request.form['quali']
        uname=request.form['uname']
        password=request.form['pass']
        qry="insert into login values(null,'%s','%s','pending')"%(uname,password)
        res=insert(qry)
        print(res)
        qry2="insert into teacher values(null,'%s','%s','%s','%s','%s','%s')"%(res,fname,lname,phone,email,qualification)
        insert(qry2) 
        return """<script>alert('Registered');window.location='/teacher_reg'</script>"""
       
    return render_template("teacher_reg.html")

@public.route("/reg_details",methods=['get','post'])
def reg_details():
    data={}
    qry3= "select * from teacher inner join login using(Login_id)"
    data['view']=select(qry3)
        
    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    else:
        action=None
        
    if action=='delete':
        qry7="delete from teacher where Teacher_id='%s'"%(id)
        print(id)
        delete(qry7)
        return """<script>alert('DELETED');window.location='reg_details'</script>"""
    
    if action=='update':
        qry="select * from teacher where teacher_id='%s'"%(id)
        res=select(qry)
        if res:
            data['up']=res
    
    if 'submit' in request.form:
        input=request.form['input']
        lname=request.form['Lname']
        phone=request.form['phone']
        email=request.form['email']
        quali=request.form['qualification']
        qry8="insert into test values(null,'%s','%s','%s','%s','%s')"%(session['login'],input,lname,phone,email,quali)
        insert(qry8)
                
        return """<script>alert('profile updated');window.location='reg_details'</script>"""

            
        
          
    return render_template("reg_details.html",data=data)

# @public.route("/update",methods=['get','post'])
# def update():
#     return render_template("update.html")