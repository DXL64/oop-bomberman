# Bài tập lớn OOP - Bomberman Game

## Giới thiệu
Yêu cầu của bài tập lớn Bomberman Game là viết một phiên bản Java mô phỏng lại trò chơi [Bomberman](https://www.youtube.com/watch?v=mKIOVwqgSXM) kinh điển của NES.

<img src="res/demo.png" alt="drawing" width="400"/>

## Sinh viên thực hiện 

| Họ và tên     | Mã sinh viên |
| ------------- | ------------ |
| [Phạm Gia Linh](https://github.com/phamgialinhlx) | 20020203     |
| [Phạm Tiến Du](https://github.com/dupham2206)  | 20020039     |
| [Đặng Xuân Lộc](https://github.com/DXL64) | 20020057     |

## Mô tả về các đối tượng trong trò chơi
Nếu bạn đã từng chơi Bomberman, bạn sẽ cảm thấy quen thuộc với những đối tượng này. Chúng được được chia làm hai loại chính là nhóm đối tượng động (*Bomber*, *Enemy*, *Bomb*) và nhóm đối tượng tĩnh (*Grass*, *Wall*, *Brick*, *Door*, *Item*).

- ![](res/sprites/player_down.png) *Bomber* là nhân vật chính của trò chơi. Bomber có thể di chuyển theo 4 hướng trái/phải/lên/xuống theo sự điều khiển của người chơi. 
- ![](res/sprites/balloom_left1.png) *Enemy* là các đối tượng mà Bomber phải tiêu diệt hết để có thể qua Level. Enemy có thể di chuyển ngẫu nhiên hoặc tự đuổi theo Bomber tùy theo loại Enemy. Các loại Enemy sẽ được mô tả cụ thể ở phần dưới.
- ![](res/sprites/bomb.png) *Bomb* là đối tượng mà Bomber sẽ đặt và kích hoạt tại các ô Grass. Khi đã được kích hoạt, Bomber và Enemy không thể di chuyển vào vị trí Bomb. Tuy nhiên ngay khi Bomber vừa đặt và kích hoạt Bomb tại ví trí của mình, Bomber có một lần được đi từ vị trí đặt Bomb ra vị trí bên cạnh. Sau khi kích hoạt 2s, Bomb sẽ tự nổ, các đối tượng *Flame* ![](res/sprites/explosion_horizontal.png) được tạo ra.


- ![](res/sprites/grass.png) *Grass* là đối tượng mà Bomber và Enemy có thể di chuyển xuyên qua, và cho phép đặt Bomb lên vị trí của nó
- ![](res/sprites/wall.png) *Wall* là đối tượng cố định, không thể phá hủy bằng Bomb cũng như không thể đặt Bomb lên được, Bomber và Enemy không thể di chuyển vào đối tượng này
- ![](res/sprites/brick.png) *Brick* là đối tượng được đặt lên các ô Grass, không cho phép đặt Bomb lên nhưng có thể bị phá hủy bởi Bomb được đặt gần đó. Bomber và Enemy thông thường không thể di chuyển vào vị trí Brick khi nó chưa bị phá hủy.


- ![](res/sprites/portal.png) *Portal* là đối tượng được giấu phía sau một đối tượng Brick. Khi Brick đó bị phá hủy, Portal sẽ hiện ra và nếu tất cả Enemy đã bị tiêu diệt thì người chơi có thể qua Level khác bằng cách di chuyển vào vị trí của Portal.

Các *Item* cũng được giấu phía sau Brick và chỉ hiện ra khi Brick bị phá hủy. Bomber có thể sử dụng Item bằng cách di chuyển vào vị trí của Item. Thông tin về chức năng của các Item được liệt kê như dưới đây:
- ![](res/sprites/powerup_speed.png) *SpeedItem* Khi sử dụng Item này, Bomber sẽ được tăng vận tốc di chuyển thêm một giá trị thích hợp
- ![](res/sprites/powerup_flames.png) *FlameItem* Item này giúp tăng phạm vi ảnh hưởng của Bomb khi nổ (độ dài các Flame lớn hơn)
- ![](res/sprites/powerup_bombs.png) *BombItem* Thông thường, nếu không có đối tượng Bomb nào đang trong trạng thái kích hoạt, Bomber sẽ được đặt và kích hoạt duy nhất một đối tượng Bomb. Item này giúp tăng số lượng Bomb có thể đặt thêm một.

Thông tin về các loại *Enemy* được liệt kê như dưới đây:

- ![](res/sprites/balloom_left1.png) *Balloom* là Enemy đơn giản nhất, di chuyển ngẫu nhiên với vận tốc 32 pixels / 64 khung hình, không thể đi xuyên bomb.
- ![](res/sprites/oneal_left1.png) *Oneal* có tốc độ di chuyển đuổi theo bomber gần nhất (sử dụng [thuật toán A*](https://en.wikipedia.org/wiki/A*_search_algorithm)) với vận tốc 32 pixels / 32 khung hình, không định vị được bomb và đi xuyên bomb.
- ![](res/sprites/doll_left1.png) *Doll* Là Enemy di chuyển ngẫu nhiên với vận tốc 32 pixels / 24 khung hình và có thể đi xuyên qua mọi object.
- ![](res/sprites/kondoria_left1.png) *Nightmare* Là Enemy có tốc độ di chuyển đuổi theo bomber gần nhất (sử dụng [thuật toán A*](https://en.wikipedia.org/wiki/A*_search_algorithm)) với vận tốc 32 pixels / 32 khung hình, định vị được bomb và lửa của bomb đồng thời né được bomb nếu đủ thời gian chạy.
- ![](res/sprites/minvo_left1.png) *Duplicate* Là Enemy có tốc độ di chuyển đuổi theo bomber gần nhất (sử dụng [thuật toán A*](https://en.wikipedia.org/wiki/A*_search_algorithm)) với vận tốc 32 pixels / 32 khung hình, không định vị được bomb và đi xuyên bomb. Khi chết thì Enemy này sẽ sinh ra 2 Balloom enemy ngay tại vị trí bị tiêu diệt



## Mô tả game play
### Điều khiển
- Game hoàn toàn điều khiển bằng bàn phím.
- Trong menu: sử dụng `W`, `S` để di chuyển giữa các chế độ chơi, `Enter` để chọn.
- Đối với Bomber sử dụng các phím `W`, `A`, `S`, `D` tương ứng với đi lên, trái, xuống, phải. `Space` để đặt bomb.
- Khi đang trong chế độ chơi Single, có thể sử dụng phím `P` để dừng lại trò chơi. Nhấn `Enter` để tiếp tục trò chơi

### Cơ chế game

- Bomber sẽ bị giết khi va chạm với Enemy hoặc thuộc phạm vi Bomb nổ. Lúc đấy trò chơi kết thúc.
- Enemy bị tiêu diệt khi thuộc phạm vi Bomb nổ
- Một đối tượng thuộc phạm vi Bomb nổ có nghĩa là đối tượng đó va chạm với một trong các tia lửa được tạo ra tại thời điểm một đối tượng Bomb nổ.
- Khi Bomb nổ, một Flame trung tâm![](res/sprites/bomb_exploded.png) tại vị trí Bomb nổ và bốn Flame tại bốn vị trí ô đơn vị xung quanh vị trí của Bomb xuất hiện theo bốn hướng trên![](res/sprites/explosion_vertical.png)/dưới![](res/sprites/explosion_vertical.png)/trái![](res/sprites/explosion_horizontal.png)/phải![](res/sprites/explosion_horizontal.png). Độ dài bốn Flame xung quanh mặc định là 1 đơn vị, được tăng lên khi Bomber sử dụng các FlameItem.
- Khi 1 quả Bomb nổ, những quả Bomb được đặt bên cạnh sẽ nổ ngay lập tức chứ không có thời gian chờ
- Khi các Flame xuất hiện, nếu có một đối tượng thuộc loại Brick/Wall nằm trên vị trí một trong các Flame thì độ dài Flame đó sẽ được giảm đi để sao cho Flame chỉ xuất hiện đến vị trí đối tượng Brick/Wall theo hướng xuất hiện. Lúc đó chỉ có đối tượng Brick/Wall bị ảnh hưởng bởi Flame, các đối tượng tiếp theo không bị ảnh hưởng. Còn nếu vật cản Flame là một đối tượng Bomb khác thì đối tượng Bomb đó cũng sẽ nổ ngay lập tức.
- 2 Items Flame và Bomb sẽ được tăng không giới hạn khi ăn, còn với Speed Item sau khi tăng đến tốc độc tối đa sẽ không được tăng tốc độ nữa.
- Các Items được kích hoạt trong một màn chơi sẽ được reset khi sang màn sau.


### Các chế độ chơi
- Có ba chế độ chơi là `Single Play`, `Multi Play` và `Survival Play`.
- Chế độ `Single Play` là chế độ một người chơi: Trong một màn chơi, Bomber sẽ được người chơi di chuyển, đặt và kích hoạt Bomb với mục tiêu chính là tiêu diệt tất cả Enemy và tìm ra vị trí Portal để có thể qua màn mới. Người chơi hoàn thành 4 Level sẽ chiến thắng.

- Trong chế độ nhiều người chơi (`Multi Play` và `Survival Play`), các Bombers (Tối đa 4 người, tối thiểu 2 người) sẽ được kết nối chung thông qua một mạng LAN. Nếu chơi qua mạng Internet thì sẽ sử dụng [Radmin VPN](https://www.radmin-vpn.com/) để ghép các mạng LAN lại thành 1 mạng LAN chung. Chủ phòng sẽ là người đầu tiên vào game. Trò chơi bắt đầu khi những người chơi khác sẵn sàng và chủ phòng sẽ bấm `Enter` để vào game.

- `Multi Play`: Các bombers sẽ được người chơi di chuyển, đặt và kích hoạt Bomb với mục tiêu chính là tiêu diệt tất cả Enemy và tìm ra vị trí Portal để có thể qua màn mới. Khi một người chơi không may mắn bị giết khi va chạm với Enemy có thể hổi sinh nếu sang màn sau. Mọi người chơi hoàn thành 4 Level sẽ chiến thắng.
- `Survial Play`: Nhiệm vụ của các bomber là giết các bomber còn lại và là người sống sót cuối cùng

### Cơ chế mạng LAN
- Sử dụng cách thức truyền tin [Multicast](https://vi.wikipedia.org/wiki/Multicast) với địa chỉ 224.0.0.224, cổng 7777. Việc sử dụng cách thức truyền tin này để tận dụng tốc độ truyền tin nhưng sẽ còn nhiều hạn chế.
- Định danh: Người chơi vào phòng đầu tiên sẽ điều khiển Bomber số 0. Những người chơi khác vào phòng sẽ gửi đi message có dạng "Hello". Những người trong phòng nhận được sẽ gửi lại message dạng "Hi" cùng mã số bomber sẽ điều khiển của người mới vào.
- Lúc Chơi: Mỗi khi người chơi nào ấn các nút di chuyển hay đặt bomb thì sẽ gửi các message dạng "A", "S", "W", "D", "Bomb" tương ứng. Và chỉ update di chuyển của bomber khi nhận được message để đảm bảo sự đồng bộ giữa các người chơi. Cách di chuyển của Enemy sẽ do client của bomber chủ phòng quyết định (random rồi gửi cho các người chơi còn lại và cũng chỉ update sau khi nhận được message).


## Tóm tắt các tính năng trong bài tập lớn
- Thiết kế cây thừa kế cho các đối tượng game
- Có thể tự xây dựng bản đồ màn chơi từ tệp cấu hình (có mẫu tệp cấu hình, xem [tại đây](https://raw.githubusercontent.com/bqcuong/bomberman-starter/starter-2/res/levels/Level1.txt))
- Di chuyển Bomber theo sự điều khiển từ người chơi
- Tự động di chuyển các Enemy
- Xử lý va chạm cho các đối tượng Bomber, Enemy, Wall, Brick, Bomb
- Xử lý bom nổ
- Xử lý khi Bomber sử dụng các Item và khi đi vào vị trí Portal
- Nâng cấp thuật toán tìm đường cho Enemy
- Phát triển thêm các loại Enemy khác (tổng cộng 5 loại)
- Xử lý hiệu ứng âm thanh (thêm music & sound effects)
- Phát triển hệ thống server-client để nhiều người có thể cùng chơi qua mạng LAN hoặc Internet
