
// 立即执行
(function(){
    if (document.getElementsByClassName("dataTables_empty").length == 1) {
        return;
    } else {
        const buttonArray = document.getElementsByClassName("btn btn-success");
        var successButtonArray = [];

        for (let i = 0; i < buttonArray.length; i++) {
            // alert(String(buttonArray[i].value).trim());
            if (String(buttonArray[i].value).trim() == "请假通过") {
                successButtonArray[successButtonArray.length] = buttonArray[i];
            }
        }
        for (let i = 0; i < successButtonArray.length; i++) {
            successButtonArray[i].click();
        }
    }
})();

