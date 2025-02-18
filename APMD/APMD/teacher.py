from flask import*
from database import*


teacher=Blueprint('teacher',__name__)

@teacher.route("/teacher_home")
def teacher_home():
    return render_template("teacher_home.html")


  
@teacher.route("/manage_test",methods=['post','get'])
def manage_test():
    data={}
    qry3= "select * from test inner join teacher using(Teacher_id) where Teacher_id='%s'"%(session['teacher'])
    data['view']=select(qry3)
    
    
    if 'submit' in request.form:
        input=request.form['input']
        qry8="insert into test values(null,'%s','%s')"%(session['teacher'],input)
        insert(qry8)
                
        return """<script>alert('ADDED');window.location='manage_test'</script>"""

    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    else:
        action=None
        
    if action=='delete':
        qry9="delete from test where test_id='%s'"%(id)
        delete(qry9)
        return """<script>alert('DELLTED');window.location='manage_test'</script>"""
    
    if action=='update':
        qry="select * from test where test_id='%s'"%(id)
        res=select(qry)
        
        data['up']=res

    if "update" in request.form:
        input=request.form['input']
        u="update test set Test_name='%s' where test_id='%s'"%(input,id)
        update(u)
        return """<script>alert('UPDATED');window.location='manage_test'</script>"""


            
            
    return render_template("manage_test.html",data=data)


@teacher.route("/view_answer",methods=['get','post'])
def view_answer():
    print("////////////////")
    data={}
    id=request.args['qid']
    a="select * from questions inner join answers using(Question_id) inner join students using(student_id) where Question_id='%s'"%(id)
    data['view']=select(a)
    print(data['view'] )
    return render_template("user_view_answer.html",data=data)



# @teacher.route("/update_test",methods=['get','post'])
# def update_test():
#     id=request.args['id']
#     if 'update' in request.form:
#         input=request.form['input']
#         qry="update test set Test_name='%s' where Test_id='%s'"%(input,id)
#         update(qry)
#         return """<script>alert('updated');window.location='manage_test'</script>"""
#     return render_template("update_test.html")
@teacher.route("/update_test",methods=['get','post'])
def update_test():
    data={}
    Test_id=request.form['test_id']
    Test=request.args['Test']
    qry="update test set Test ='%s' where Test_id='%s'"%(Test,Test_id)

    res=update(qry)
    if res:
        data['status']='success'
        data['data']=res
    else:
        data['status']='failed'
        

@teacher.route("/add_test",methods=['get','post'])
def add_test():
    id=request.args['id']
    if 'add' in request.form:
        input=request.form['input']
        qry="insert into test values(null,'%s','%s')"%(session['teacher'],input)
        insert(qry)
        return """<script>alert('added');window.location='manage_test'</script>"""
        
    return render_template("add_test.html")

  
  
@teacher.route("/teacher_send_comp",methods=['get','post'])
def teacher_send_comp():
    data={}
    qry="select * from complaints where sender_id='%s'"%(session['log'])
    res=select(qry)
    data['u']=res
    print(data['u'])
    if 'complaints' in request.form:
        title=request.form['title']
        des=request.form['description']
        qry="insert into complaints values(null,'%s','%s','%s','pending',curdate())"%(session['log'],title,des)
        insert(qry)
        return redirect(url_for('teacher.teacher_send_comp'))
    return render_template("teacher_send_comp.html",data=data)

@teacher.route("/manage_qstn",methods=['post','get'])
def manage_qstn():
    tid=request.args['id']
    data={}
    qry="select * from questions inner join test using (Test_id) where Teacher_id='%s'"%(session['teacher'])
    data['view']=select(qry)
    res=select(qry)
    # if res:
    #     data['view']=res
    
    if 'submit' in request.form:
        input=request.form['input']
        ans=request.form['ans']
        qry8="insert into questions values(null,'%s','%s','%s')"%(tid,input,ans)
        insert(qry8)
        
        return """<script>alert('ADDED');window.location='manage_test'</script>"""
    
    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    else:
        action=None
        
    if action=='delete':
        qry9="delete from questions where Question_id='%s'"%(id)
        delete(qry9)
        return """<script>alert('DELETED');window.location='manage_test'</script>"""
    
    if action=='update':
        qry="select * from questions where Question_id='%s'"%(id)
        res=select(qry)
        
        data['up']=res

    if 'update' in request.form:
                        
        Question=request.form['input']
        ans=request.form['ans']
        qry_update = "update questions set Question='%s',Answer='%s' where Question_id='%s'" % (Question,ans,id)
        update(qry_update)
        return """<script>alert('UPDATED');window.location='manage_test'</script>"""
    return render_template("manage_qstn.html",data=data)


@teacher.route("/teacher_view_participants",methods=['get','post'])
def view_participants():
    data={}
    qry="select * from answers inner join questions using(Question_id) inner join test using(Test_id)inner join students using(student_id) where Teacher_id='%s'"%(session['teacher'])

    res=select(qry)
    data['view']=res
    return render_template('teacher_view_participants.html',data=data)    
    
     
