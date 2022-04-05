package backend.kotlintesting.service

import backend.kotlintesting.model.TempResultCandidate
import backend.kotlintesting.repo.CandiDisplayRepo
import backend.kotlintesting.repo.QuestionRepo
import backend.kotlintesting.repo.TempResultRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CandidateService(@Autowired private val tempResultRepo: TempResultRepo,
                       @Autowired private val questionRepo: QuestionRepo,
                       @Autowired private val candiDisplayRepo: CandiDisplayRepo
) {

    // #################################################################
    // #                             _`
    // #                          _ooOoo_
    // #                         o8888888o
    // #                         88" . "88
    // #                         (| -_- |)
    // #                         O\  =  /O
    // #                      ____/`---'\____
    // #                    .'  \\|     |//  `.
    // #                   /  \\|||  :  |||//  \
    // #                  /  _||||| -:- |||||_  \
    // #                  |   | \\\  -  /'| |   |
    // #                  | \_|  `\`---'//  |_/ |
    // #                  \  .-\__ `-. -'__/-.  /
    // #                ___`. .'  /--.--\  `. .'___
    // #             ."" '<  `.___\_<|>_/___.' _> \"".
    // #            | | :  `- \`. ;`. _/; .'/ /  .' ; |
    // #            \  \ `-.   \_\_`. _.'_/_/  -' _.' /
    // #=============`-.`___`-.__\ \___  /__.-'_.'_.-'=================#
    //                            `=--=-'
    //           _.-/`)
    //          // / / )
    //       .=// / / / )
    //      //`/ / / / /
    //     // /     ` /
    //    ||         /
    //     \\       /
    //      ))    .'
    //     //    /
    //          /

    fun saveAllResult(finalRes: MutableList<TempResultCandidate>): List<TempResultCandidate> = tempResultRepo.saveAll(finalRes)
    fun setIsDone(idCandidate: Int) = candiDisplayRepo.setIsDone(idCandidate)
    fun getTypeQuestion(idQuestion: Int): Int? = questionRepo.getById(idQuestion).type
}