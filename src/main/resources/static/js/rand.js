function makeRandomstr() {
    var length = document.getElementById('length').value;
    var str = array_merge(range('a', 'z'), range('A', 'Z'), range('0', '9'));
    var r_str = '';
    for($i = 0; $i < length; $i++){
        r_str = str[rand(0, count(str) - 1)];
    }
    document.getElementById('output').innerHTML = r_str;
}