package board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import board.dto.RestBoardDto;
import board.service.RestBoardService;

@Controller
public class RestBoardController {
//	REST api - REpersentational State Transfer의 약자로 기존의 웹 아키텍처가 HTTP 본래의 우수성을 잘 활용하지 못한다고 생각하여 그 장점을 최대한 활용할 수 있는 아키텍처로 REST를 만듬
//	HTTP URI로 리소스를 정의하고 HTTP 메서드로 리소스에 대한 행위를 정의
//	리소스 : 서비스를 제공하는 시스템의 자원을 의미, URI로 정의(URI는 명사를 사용)
	
//	REST API는 GET, POST, DELETE, PUT 에 각각의 기능을 부여하여 사용함
//	POST : Create의 의미, 리소스를 생성
//	GET : Read의 의미, 해당 URI의 리소스를 조회
//	PUT : Update의 의미, 해당 URI의 리소스를 수정
//	DELETE  : Delete의 의미, 해당 URI의 리소스를 삭제
	
//	리소스가 '/members'와 같이 존재한다고 할 경우 기존 방식으로 데이터를 요청할 경우 GET/members/select/1 형태로 사용하게 됨
//	기존 방식대로하여 삭제하고자 하면 GET /members/delete/1 이런 형식으로 사용해야 함
//	REST 방식으로 사용하면 GET /members/1로 사용가능하고, 삭제 시 DELETE/members/1로 사용이 가능함
	
//	URI 부분은 영문 소문자로만 표현, 가독성을 위해서 '-'을 사용해도 상관없음('_'는 안됨)
	
//	@RequestMapping에 value와 method를 사용하여 View 영역과 매칭하고, REST api를 사용하여 조회 기능을 하는 GET 방식을 사용함
//	REST api에서는 method의 방식에 따라서 실행되는 역활이 다르기 때문에 method 부분이 반드시 필요함
//	@RequestMapping 어노테이션 대신 @GetMapping, @PostMapping, @PutMapping, @DeleteMapping 어노테이션을 사용하면 method 부분을 생략할 수 있음
	
	@Autowired
	private RestBoardService restBoardService;
	
	@RequestMapping(value="/board", method=RequestMethod.GET)
	public ModelAndView openRestBoardList() throws Exception {
		ModelAndView mv = new ModelAndView("/board/restBoardList");
		
		List<RestBoardDto> list = restBoardService.selectRestBoardList();
		mv.addObject("datas", list);
		
		return mv;
	}
	
//	기존 방식에서는 글번호를 입력할 경우 url를 입력하고 ? 파라미터명=파라미터값을 넣는 형태로 사용하였지만 rest방식에서는 url이후에 /파라미터값을 넣는 형태로 사용
//	/board.do?boardIdx="1"(기존방식) -> /board/1(rest api)
//	@PathVariable("boardIdx") 는 boardIdx라는 파라미터 값을 uri 부분에 있는 {boardIdx}라는 것과 연동 시킴
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView openRestBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
		ModelAndView mv = new ModelAndView("/board/restBoardDetail");
		
		RestBoardDto data = restBoardService.selectRestBoardDetail(boardIdx);
		mv.addObject("data",data);	
		
		return mv;
	}
	
//	@ResestMapping에서 주소 부분인 value값이 /board/write를 사용한 메서드가 writeRestBoard, 
//	insertRestBoard 2개가 있지만 두 메서드는 각각 RequestMethod.GET RequestMethod.POST를 사용하고 있기 때문에 서로 다른 기능을 가지고 있음
//	사용자 입력을 위한 view 부분
	@RequestMapping(value="/board/wirte", method=RequestMethod.GET)
	public String writeRestBoardWrite() throws Exception {
		return "/board/restBoardWrite";
		
	}
	
//	사용자가 form을 통해서 전송한 데이터를 넘겨받아 DB로 다시 전송하는 부분
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String insertRestBoard(RestBoardDto data) throws Exception {
		restBoardService.insertRestBoard(data);
		
		return "redirect:/board";
	}
	
//	@RequestMapping의 method가 PUT이기 때문에 DB 수정 기능을 사용함
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.PUT)
	public String updateRestBoard(RestBoardDto data) throws Exception {
		restBoardService.updateRestBoard(data);
		
		return "redirect:/board";
	}
	
//	@RequestMapping의 method를 DELETE로 사용하여 DB삭제 기능 사용
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.DELETE)
	public String deleteRestBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		restBoardService.deleteRestBoard(boardIdx);
		
		return "redirect:/board";
	}
	
	@RequestMapping("/")
	public String index() {
		return "/board/index";
	}
}
