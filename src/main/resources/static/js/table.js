$('#webInfoTable td').livepaths('click',function(){
    var $table_td = $(this)[0];
    var $table_tr = $(this).closest('tr')[0];
    console.log($table_td);
    console.log($table_tr);
}
);