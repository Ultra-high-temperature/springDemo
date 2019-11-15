function post() {
    var question_id = $("input:hidden[name='question_id']").val();
    var content = $("#content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": question_id,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if(response.code==200){
                $("#comment_section").hide()
            }
            else {
                alert(response.code,response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });

}