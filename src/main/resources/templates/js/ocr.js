$(function () {
    // submit 했을 때 처리
    $('#ocrForm').on('submit', function (event) {
        event.preventDefault();
        var formData = new FormData($('#ocrForm')[0]);
        var fileName = $('#uploadFile').val().split("\\").pop();
        $.ajax({
            type : "post",
            enctype : "multipart/form-data",
            url : "clovaOCR",
            data : formData,
            processData : false, // 필수
            contentType : false, // 필수
            success:function (result) {
                $('#resultDiv').text(result);
                // 이미지 출력 (div에 append)
                $('#resultImg').empty();
                $('#resultImg').append('<img src="/images/'+fileName+'"/>');
            },
            error:function (e) {
                alert("오류 발생" + e);
            }
        });
    })
})