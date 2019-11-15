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
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=3ac5674bffa06cec9a3b&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                    break;

            }
            console.log(response);
        },
        dataType: "json"
    });

}