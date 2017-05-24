function makeRandStr(num){
    temp = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    randStr = '';
    for(i=0;i<num;i++){
        randStr += temp.charAt(Math.floor(Math.random()*temp.length));
    }
    document.randForm.output.value = randStr;
}