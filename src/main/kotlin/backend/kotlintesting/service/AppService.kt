package backend.kotlintesting.service

import backend.kotlintesting.model.Staff
import backend.kotlintesting.repo.CandiDisplayRepo
import backend.kotlintesting.repo.StaffRepo
import org.springframework.stereotype.Service

@Service
class AppService(val staffRepo: StaffRepo, val candiDisplayRepo: CandiDisplayRepo) {

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

    fun findByUsernameAndPassword(username: String,password: String): Staff? = staffRepo.findByUsernameAndPassword(username,password)
    fun login(username:String,password:String) : Boolean {
        if(staffRepo.findByUsernameAndPassword(username,password)!=null) {
            println("Dang nhap thanh cong")
            println(staffRepo.findByUsernameAndPassword(username,password))
            return true
        }
        return false
    }

    fun getJoinCandidate(idCandidate: Int) = candiDisplayRepo.getById(idCandidate)

}