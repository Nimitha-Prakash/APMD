from flask import*
from database import*

app=Flask(__name__)

admin=Blueprint('admin',__name__)

@admin.route("/admin_home")
def admin_home():
    return render_template("admin_home.html")

@admin.route("/verify_teacher",methods=['post','get'])
def manage_test():
    data={}
    qry3= "select * from teacher inner join login using(Login_id) "
    data['view']=select(qry3)
    
    
    if 'submit' in request.form:
        input=request.form['input']
        qry8="insert into teacher values(null,'%s','%s','%s','%s','%s','%s)"%(session['login'],input)
        insert(qry8)
                
        return """<script>alert('Accepted');window.location='verify_teacher'</script>"""

    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    else:
        action=None
        
    if action=='reject':
        qry9="delete from teacher where Login_id='%s'"%(id)
        delete(qry9)
        l="delete from login where login_id='%s'"%(id)
        delete(l)
        return """<script>alert('REJECTED');window.location='verify_teacher'</script>"""
 
    
    if action=='accept':
        qry9="update login set user_type='teacher' where login_id='%s'"%(id)
        delete(qry9)
        return """<script>alert('ACCPETED');window.location='verify_teacher'</script>"""
           
    return render_template("verify_teacher.html",data=data)

@admin.route('/view_complaints',methods=['get','post'])
def view_complaints():
    data={}
    qry="select * from complaints"
    res=select(qry)
    data['view']=res
    return render_template('view_complaints.html',data=data)

@admin.route("/send_reply",methods=['get','post'])
def send_reply():
    id=request.args['id']
    if 'sendreply' in request.form:
        reply=request.form['reply']
        qry="update complaints set reply='%s' where complaint_id='%s'"%(reply,id)
        update(qry)
        return """<script>alert('reply send successfully');window.location="/view_complaints"</script>"""
        
    return render_template("send_reply.html")

@admin.route("/view_students",methods=['get','post'])
def view_students():
    data={}
    qry="select * from students"
    res=select(qry)
    data['view']=res
    return render_template('view_students.html',data=data)
    
@admin.route("/view_participants",methods=['get','post'])
def view_participants():
    data={}
    qry="select * from answers inner join questions using(Question_id) inner join test using(Test_id)inner join students using(student_id) where Teacher_id='%s'"%(session['teacher'])
    # qry="select * from answers"
    res=select(qry)
    data['view']=res
    return render_template('view_participants.html',data=data)
        
        
    

