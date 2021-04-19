function post() {
    var questionId = $("#questionId").val();
    var content = $("#content").val();

    if (content === '') {
        swal({
            text: "回复内容不能为空",
            type: "warning",
            confirm:'ok',
            confirmButtonColor: '#e6c43c',
        });
    } else {
        $.ajax({
            type: "POST",
            url: "/comment",
            contentType: 'application/json',
            data: JSON.stringify({
                "parentId": questionId,
                "type": 1,
                "content": content
            }),
            success: function (data) {
                if (data.code === 200) {
                    window.location.reload();
                } else {
                    swal({
                        text: "出现了错误",
                        type: "error",
                        confirm:'ok',
                        confirmButtonColor: '#cc1c59',
                    });
                }
            },
            dataType: "json"
        });
    }
}

function secondComment(e) {
    var id = e.getAttribute("data-id");
    var check = e.getAttribute("data-check");
    var comment = $("#comment-" + id);
    //如果check为1则展开二级评论，否则收缩
    if (check === "1") {
        $.getJSON("/comment/" + id, function (data) {
            var subCommentContainer = $("#comment-" + id);
            //如果子元素的长度为1，即第一次添加，则调用下面的方法
            if (subCommentContainer.children().length === 1) {
                $.each(data.data.reverse(), function (index, comment) {
                    //对应<span th:text="${comment.user.name}"/>
                    var usernameElement = $("<span/>", {
                        html: comment.user.username+"  回复  "+comment.commentorName
                    });
                    //对应<div style="font-size: 15px; margin-top:5px;"
                    //      th:text="${comment.content}">
                    //    </div>
                    var contentElement = $("<div/>", {
                        "style": "font-size: 15px; margin-top:5px;",
                        html: comment.content
                    });

                    var timeElement = $("<span/>", {
                        "style": "float: right",
                        html: moment(comment.createTime).format('YYYY-MM-DD HH:mm')
                    });

                    var questionmenuElement = $("<div/>", {
                        "class": "question-menu"
                    }).append(timeElement);

                    var imgElement = $("<img/>", {
                        "class": "media-object img-circle picset",
                        "src": "get/" + comment.user.headPicture
                    }).bind("error",function () {
                        this.src = 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimgqn.koudaitong.com%2Fupload_files%2F2015%2F03%2F15%2FFprrhVQiB__Y6bTLcKmtuDQK9Gl0.jpg&refer=http%3A%2F%2Fimgqn.koudaitong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620005599&t=8af3bffa5ff3893618cef3ac4395e184';
                    });

                    var medialeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append(imgElement);

                    var mediaheadingElement = $("<h4/>", {
                        "class": "media-heading",
                    }).append(usernameElement)
                        .append(contentElement)
                        .append(questionmenuElement);

                    var mediabodyElement = $("<div/>", {
                        "class": "media-body",
                    }).append(mediaheadingElement);

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(medialeftElement)
                        .append(mediabodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-ss-12 comments",
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                })
            }
        });
        comment.addClass("in");
        e.setAttribute("data-check", "0");
        e.classList.add("active");

    } else {
        comment.removeClass("in");
        e.setAttribute("data-check", "1")
        e.classList.remove("active");
    }
}

function replyPost(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    if (content === '') {
        swal({
            text: "回复内容不能为空",
            type: "warning",
            confirm:'ok',
            confirmButtonColor: '#e6c43c',
        });
    } else {
        $.ajax({
            type: "POST",
            url: "/comment",
            contentType: 'application/json',
            data: JSON.stringify({
                "parentId": commentId,
                "type": 2,
                "content": content
            }),
            success: function (data) {
                if (data.code === 200) {
                    window.location.reload();
                } else {
                    swal({
                        text: "出现了问题",
                        type: "error",
                        confirm:'ok',
                        confirmButtonColor: '#d620a0',
                    });
                }
            },
            dataType: "json"
        });

        console.log(commentId);
        console.log(content);
    }
}
function showSelectTag() {
    $("#select-tags").show();
}
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    //获取当前的标签内容
    var previous = $("#tag").val();
    //如果标签不存在，则添加
    if (previous.indexOf(value) === -1){
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}
//方法暂时搁置
function searchQuestion() {
    //获取到搜索数据
    var search = $("#search").val();
    if (search === '') {
        alert("搜索内容不能为空");
    } else {
        $.ajax({
            type: "post",
            url: "/search",
            dataType:"json",
            data: {
                searchData:search
            },
            success: function (data) {
                if (data.flag === 1) {
                    window.location.href="blog/search";
                } else {
                    swal({
                        text: "出现了问题",
                        type: "warning",
                        confirm:'ok',
                        confirmButtonColor: '#e6c43c',
                    });
                }
            }
        });
    }
}
//点赞
function increaseGreat(e) {
   var commentId = e.id;
   var questionId = document.getElementById("question-id").innerHTML;
   var greatNumber = document.getElementById(commentId).innerHTML;
   var number = Number(greatNumber);
    $.ajax({
        type: "post",
        url: "/increaseGreat",
        dataType:"json",
        data: {
            commentId:commentId,
            questionId:questionId
        },
        success: function (data) {
            if (data.flag === 1) {
              number++;
                e.classList.add("active");
              document.getElementById(commentId).innerHTML = number.toString();
            } else if(data.flag === 2){
                number--;
                e.classList.remove("active");
                document.getElementById(commentId).innerHTML = number.toString();
            }else {
                swal({
                    text: "出现了问题",
                    type: "warning",
                    confirm:'ok',
                    confirmButtonColor: '#e6c43c',
                });
            }
        }
    });
}
