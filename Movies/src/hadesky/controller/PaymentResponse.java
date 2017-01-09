//package hadesky.controller;
//
//import hadesky.domain.Order;
//import hadesky.service.Business;
//import hadesky.service.impl.BusinessImpl;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import utli.PaymentUtil;
//
////����֧�����
//public class PaymentResponse extends HttpServlet {
//	private Business s = new BusinessImpl();
//	public void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		//��ȡ����ݹ��������
//		String p1_MerId = request.getParameter("p1_MerId");
//		String r0_Cmd= request.getParameter("r0_Cmd");
//		String r1_Code= request.getParameter("r1_Code");//1���ɹ�
//		String r2_TrxId= request.getParameter("r2_TrxId");
//		String r3_Amt= request.getParameter("r3_Amt");
//		String r4_Cur= request.getParameter("r4_Cur");
//		String r5_Pid= request.getParameter("r5_Pid");
//		String r6_Order= request.getParameter("r6_Order");//������
//		String r7_Uid= request.getParameter("r7_Uid");
//		String r8_MP= request.getParameter("r8_MP");
//		String r9_BType= request.getParameter("r9_BType");//Ϊ��1��: ������ض���;Ϊ��2��: ��������Ե�ͨѶ.
//		String hmac= request.getParameter("hmac");
//		
//		boolean b = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
//		if(b){
//			//���û�б��۸�
//			if("1".equals(r1_Code)){
//				//�ɹ�
//				if("2".equals(r9_BType)){
//					response.getWriter().write("success");
//				}
//				//��Ķ���״̬
//				Order order = s.findOrderByOrderNum(r6_Order);
//				if(order==null){
//					response.getWriter().write("����������");
//					return;
//				}
//				order.setStatus(1);
//				s.updateOrder(order);
//				response.getWriter().write("���׳ɹ���2�뷵����ҳ");
//				response.setHeader("Refresh", "2;URL="+request.getContextPath());
//			}else{
//				response.getWriter().write("����ʧ��");
//			}
//		}else{
//			response.getWriter().write("֧���п��ܳɹ��������ص���Ϣ���ܱ��۸ġ�");
//		}
//	}
//
//	public void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		doGet(request, response);
//	}
//
//}
