// 意思是空的;
if (document.getElementsByClassName("dataTables_empty").length == 1) {

}

buttonArray = document.getElementsByClassName("btn btn-success");

var successButtonArray = [];

for (let i = 0; i < buttonArray.length; ++i) {
    if (buttonArray[i].value.toString() == "请假通过") {
        successButtonArray[successButtonArray.length] = buttonArray[i];
    }
}
