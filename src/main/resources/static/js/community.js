//提交回复
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
            switch (response.code) {
                case 200:
                    $("#comment_section").hide();
                    break;
                case 2003:
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=3ac5674bffa06cec9a3b&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                    break;

            }
            console.log(response);
        },
        dataType: "json"
    });
}
//提交回复2
function comment(e) {
    var commentId = e.getAttribute("data-id");
    console.log(commentId);
    var inputId = '#input-' + commentId;
    // console.log(inputId);
    // debugger;
    var content = $(inputId).val();
    console.log(content);

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": commentId,
            "content": content,
            "type": 2
        }),
        success: function (response) {
            // switch (response.code) {
            //     case 200:
            //         $("#comment_section").hide();
            //         break;
            //     case 2003:
            //         var isAccepted = confirm(response.message);
            //         if (isAccepted){
            //             window.open("https://github.com/login/oauth/authorize?client_id=3ac5674bffa06cec9a3b&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
            //             window.localStorage.setItem("closable",true);
            //         }
            //         break;
            // }
            console.log(response);
        },
        dataType: "json"
    });
}
//展开回复
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    console.log(id);
    var comment = $('#comment-' + id)
    var collapse = e.getAttribute("data-collapse");
    if (collapse != null) {
        //折叠二级评论
        e.removeAttribute("data-collapse");
        comment.removeClass("in");
    } else {
        $.getJSON("/comment/" + id, function (data) {
            console.log(data);
            var subCommentContainer = $("#comment-"+id);
            $.each(data.data, function (index,comment) {
                var c = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 col-xs-12",
                    html: comment.content
                });
                subCommentContainer.append(c);
            });
        });
        //展开二级评论
        comment.addClass("in");
        //标记二级评论展开状态
        e.setAttribute("data-collapse", "in");
    }
}

function addTag(value) {
    var previous = $('#tag').val();
    if(previous.indexOf(value)==-1){
        var v=previous+value+';'
        $('#tag').val(v);
    }
}