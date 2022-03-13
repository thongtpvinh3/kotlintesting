package backend.kotlintesting.URI

class URI {

    //------------------------STAFF----------------------------

    val STAFF_TO_STAFFVIEW = "/home"
    val STAFF_LOG_OUT = "/logout"

    val STAFF_GETCANDIDATE_ALL = "/candidate"
    val STAFF_GETCANDIDATE_BY_ID = "/candidate/{idCandidate}"
    val STAFF_GETCANDIDATE_BY_NAME = "/candidate/{name}"
    val STAFF_GETCANDIDATE_BY_PHONE = "/candidate/{phone}"
    val STAFF_GETCANDIDATE_BY_EMAIL = "/candidate/{email}"

    val STAFF_ADD_CANDIDATE = "/addcandidate"
    val STAFF_DELETE_CANDIDATE = "/deletecandidate/{idCandidate}"

    val STAFF_TO_TEST_VIEW = "/testview"

    val STAFF_GETTEST_ALL = "/test"
    val STAFF_GETTEST_BY_ID = "/test/{idTest}"
    val STAFF_GETTEST_BY_NAME =  "/test/name/{name}"
    val STAFF_GETTEST_BY_CODE = "/test/code/{codeTest}"
    val STAFF_GETTEST_BY_SUBJECT = "/test/subject/{idSubject}"
    val STAFF_GETTEST_BY_DONE =  "/test/done/{isDone}"
    val STAFF_GETTEST_BY_LEVEL = "/test/level/{level}"
    val STAFF_GETTEST_BY_CANDIDATE = "/test/candidate/{idCandidate}"

    val STAFF_ADD_TEST = "/addtest"
    val STAFF_UPDATE_TEST = "/updatetest"

    val STAFF_GETQUESTION_ALL = "/question"
    val STAFF_GETQUESTION_BY_ID = "/question/{idQuestion}"
    val STAFF_GETQUESTION_BY_TYPE = "/question/type/{idType}"
    val STAFF_GETQUESTION_BY_SUBJECT = "/question/subject/{idSubject}"
    val STAFF_GETQUESTION_BY_LEVEL = "/question/level/{idLevel}"
    val STAFF_GETQUESTION_BY_TEST = "/question/test/{idTest}"

    val STAFF_ADD_QUESTION = "/addquestion"
    val STAFF_DELETE_QUESTION = "/deletequestion/{idQuestion}"
    val STAFF_UPDATE_QUESTION = "/updatequestion"
    val STAFF_ADD_ANS_FOR_QUESTION = "/addanswer/{idAnswer}/{idQuestion}"
    val STAFF_ADD_ESSAY_ANSWER_TO_QUESTION = "/addeanswer/question/{idQuestion}"
    val STAFF_ADD_MC_ANSWER_TO_QUESTION = "/addmcanswer/question/{idQuestion}"
    val STAFF_GETMCANSWER_ALL = "/mcanswer"
    val STAFF_UPDATE_MC_ANSWER = "/updatemcanswer/{idAnswer}"
    val STAFF_DEL_A_MC_ANSWER = "/deletemcanswer/{idAnswer}"
    val STAFF_GET_ESSAY_ANSWER_ALL = "/eanswer"
    val STAFF_UPDATE_ESSAY_ANSWER = "/updateessayanswer/{idAnswer}"
    val STAFF_REMOVE_MC_ANSWER = "/removemcanswer/{idAnswer}"

    val STAFF_GET_SUBJECT_ALL = "/subject"
    val STAFF_GET_LEVEL_ALL = "/level"
    val STAFF_ADD_SUBJECT = "/addsubject"
    val STAFF_DELETE_SUBJECT = "/deletesubject/{idSubject}"
    val STAFF_DELETE_LEVEL = "/deletelevel/{idLevel}"

}
