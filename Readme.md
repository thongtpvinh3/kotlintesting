# Testing online 
###### Start up environment:
Start up project:
 1. run TestingOnlineApplication
 2. Test API in Postman with file testingonline test Collection.postman_collection.json
 3. Import file Collection.postman_collection.json to Postman app
 4. to use Staff APIs, please login with API checklogin username: "luubi" and password = "123"
 5. Bảng CSDL test (H2-Database)<br>
 ![image](https://user-images.githubusercontent.com/65841739/150341180-de7e06eb-ae49-40ff-8e3f-7af084139dd0.png) <br>
 ![image](https://user-images.githubusercontent.com/65841739/150343006-0ef41ecb-34d0-45df-97dd-bc579e898abc.png)<br>
 ![image](https://user-images.githubusercontent.com/65841739/150345143-ce51c0f3-ffbf-4a3d-971c-7bd8a1d6b653.png)<br>
 ![image](https://user-images.githubusercontent.com/65841739/150345209-1968c535-3ff9-498d-ba66-b6f5c3a8bec4.png)<br>
![image](https://user-images.githubusercontent.com/65841739/150346437-b08a8c4e-eeff-4fac-aa53-94552efdc3ba.png)





###### Bước 1: Thử add đáp án cho 1 câu hỏi nhiều lựa chọn
 1. trong Postman chọn API "add mc answer to question have id" 
 2. Thử thêm 2 đáp án lần lượt có isTrue = 0 và 1 trong phần JSON ( Hoặc thêm tùy ý nhiều đáp án )
 3. Có thể thêm đáp án cho câu hỏi khác bằng cách đổi id câu hỏi trong URL
###### Bước 2: Thử add 1 câu hỏi nhiều lựa chọn cho 1 bài Test (Trùng Level)
 1. trong Postman chọn API "add question to test"
 2. gửi đi để thêm câu hỏi có id 1 cho bài test có id 1
 3. Có thể thêm nhiều câu hỏi khác bằng cách đổi id câu hỏi (PathVariable thứ 2) trong URL
###### Bước 3: Thử add 1 bài Test cho 1 ứng viên
 1. trong Postman chọn API "add test for candidate"
 2. gửi đi để thêm bài test có id = 1 cho ứng viên có id = 1 (Trùng Level)
 3. Có thể thêm bài test khác cho ứng viên khác bằng cách thay đổi PathVariable trong URL "/staff/addtestforcandidate/1/2" 1: id bài test || 2: id ứng viên
## Test phần làm bài của ứng viên
 1. Mở Redis-Server lên để test
 1. trong Postman chọn API "join by test code"
 2. trong phần x-www-form urlencoded : thêm key: code và value: mã của bài Test (Đã có sẵn bài test có mã ENG1) -> Gửi đi
 3. Có thể xem bài test hiện tại . Chọn API "get this join test"
 4. Làm 1 câu hỏi: chọn API "Candidate Do A question and Save to Redis". Mẫu JSON gửi đi bao gồm <br>
    {<br> 
      "idAnswer" : id của câu trả lời ,<br>
      "isTrue": câu trả lời (cả Trắc nghiệm và tự luận), <br>
      "type" : loại câu trả lời (TN: 0, TL:1), <br>
      "idCandidate": cố định <br>
    }<br>
    *** Có thể sửa đổi câu trả lời: đổi id câu trả lời và "isTrue" 
 6. Khi đang làm mà hết thời gian :  xóa Cache và lưu vào CSDL --> Lưu lại KQ
 7. muốn nộp bài: chọn API "submit test"
 8. Có thể xem các đáp án đang được lưu trong Cache --> chọn API: "get All cache ans"
 9. Chỉnh sửa lại thời gian bắt đầu làm bài và thời gian làm bài trong Source code: testingonline.backend.fakedatabase.TestDatabase
## Test phần chấm điểm
 1. trong Postman chọn API "get all result" để xem các đáp án đã được lưu
 2. chọn API "review MC question in Test" để chấm điểm phần trắc nghiệm của bài test (Thay đổi bài test bằng cách đổi PathVariable(id bài test cần review) trên URL
 3. chọn API "review an Essay Question" để chấm điểm cho phần tự luận ( điểm tự mình chấm. Đang để phần TN 50: TL:50 nên điểm chấm dựa vào số lượng câu hỏi TL, VD: Nếu phần TL       có 2 câu thì mỗi câu chỉ được set : <=25). URL: staff/reviewessayquestion/1/1/20 : 1 đầu tiên là id bài test || 1 thứ 2: là id câu hỏi Tự luận || 20 : là số điểm
 4. Set điểm cho Ứng viên: chọn API "set mark for candidate" với PathVariable là id ứng viên. Tự động tìm và cập nhật điểm cho ứng viên theo từng phần
