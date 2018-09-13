$(document).ready(function(){
    $(document).on("click","#test",function(event){
        $.ajax({
            type: "GET",
            url: "https://tercy.herokuapp.com/api/examples",
            timeout: 600000,
            success: function (data) {
                console.log(data);
            }
        });
    });
});