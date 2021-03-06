/**
 * 
 */
package ncsu.dbms.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ravi
 *
 */
public class OracleDataAdapter {
	OracleDb oracleDb = new OracleDb();

	public OracleDataAdapter() {

	}

	public ArrayList<User> GetUsers() {
		ArrayList<User> listUsers = new ArrayList<User>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb.GetResultSet("Select * from csc_User");
		try {
			while (resultset.next()) {
				User user = new User();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				user.UserId = resultset.getInt("User_ID");
				user.UserFName = resultset.getString("USER_FNAME");
				user.UserMName = resultset.getString("USER_MNAME");
				user.UserLName = resultset.getString("USER_LNAME");
				user.UserEmail = resultset.getString("USER_EMAIL");
				user.user_id_char = resultset.getString("user_id_char");
				user.UserIsActive = resultset.getString("User_ID") == "T" ? true
						: false;
				try {
					user.UserCreatedDate = simpleDateFormat.parse(resultset
							.getString("User_CREATEDDATE"));
					user.UserLastModifiedDate = simpleDateFormat
							.parse(resultset.getString("USER_LASTMODIFIEDDATE"));
				} catch (Exception e) {

				}
				user.UserLastModifiedBy = resultset.getInt("USER_MODIFIEDBY");
				listUsers.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listUsers;
	}

	public User GetUserDetails(String userName) {
		User user = new User();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from csc_User where user_id_char='"
						+ userName + "'");
		try {
			while (resultset.next()) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				user.UserId = resultset.getInt("User_ID");
				user.UserFName = resultset.getString("USER_FNAME");
				user.UserMName = resultset.getString("USER_MNAME");
				user.UserLName = resultset.getString("USER_LNAME");
				user.UserEmail = resultset.getString("USER_EMAIL");
				user.user_id_char = resultset.getString("user_id_char");
				user.UserIsActive = resultset.getString("User_ID") == "T" ? true
						: false;
				try {
					user.UserCreatedDate = simpleDateFormat.parse(resultset
							.getString("User_CREATEDDATE"));
					user.UserLastModifiedDate = simpleDateFormat
							.parse(resultset.getString("USER_LASTMODIFIEDDATE"));
				} catch (Exception e) {
				}
				user.UserLastModifiedBy = resultset.getInt("USER_MODIFIEDBY");
			}
		} catch (SQLException e) {
		} finally {
			oracleDb.CloseConnection();
		}
		return user;
	}

	public User GetUserDetails(String userName, String password) {
		User user = new User();
		oracleDb.OpenConnection();
		String query = "Select * from csc_User where USER_ID_CHAR='" + userName
				+ "' and USER_PASSWORD ='" + password + "'";
		ResultSet resultset = oracleDb.GetResultSet(query);
		try {
			while (resultset.next()) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				user.UserId = resultset.getInt("User_ID");
				user.UserFName = resultset.getString("USER_FNAME");
				user.UserMName = resultset.getString("USER_MNAME");
				user.UserLName = resultset.getString("USER_LNAME");
				user.UserEmail = resultset.getString("USER_EMAIL");
				user.user_id_char = resultset.getString("USER_ID_CHAR");
				user.UserIsActive = resultset.getString("User_ID") == "T" ? true
						: false;
				try {
					user.UserCreatedDate = simpleDateFormat.parse(resultset
							.getString("User_CREATEDDATE"));
					user.UserLastModifiedDate = simpleDateFormat
							.parse(resultset.getString("USER_LASTMODIFIEDDATE"));
				} catch (Exception e) {
				}
				user.UserLastModifiedBy = resultset.getInt("USER_MODIFIEDBY");
			}
		} catch (SQLException e) {
		} finally {
			oracleDb.CloseConnection();
		}
		return user;
	}

	// insert user
	public boolean InsertUser(User user) {
		User tempUser = new User();
		tempUser = User.GetUser(user.UserEmail);
		if (tempUser.UserId == 0) {
			String query = "insert into csc_user (User_ID,USER_FNAME,USER_MNAME,USER_LNAME,USER_EMAIL,USER_PASSWORD,USER_ACTIVE,USER_CREATEDDATE,USER_MODIFIEDBY,USER_LASTMODIFIEDDATE,USER_ID_CHAR)";
			query = query
					+ " values(csc_user_sequence.nextval,'"
					+ user.UserFName
					+ "','"
					+ user.UserMName
					+ "','"
					+ user.UserLName
					+ "','"
					+ user.UserEmail
					+ "','"
					+ user.UserPassword
					+ "','T',TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'),1,TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'),'"
					+ user.UserEmail.substring(0, user.UserEmail.indexOf("@"))
					+ "')";
			return oracleDb.InsertQuery(query);
		} else
			return false;
	}

	// returns question bank
	// CSC_QUESTIONBANK
	public ArrayList<QuestionBank> GetQuestionBank() {
		QuestionBank questionBank;
		ArrayList<QuestionBank> listQuestionBank = new ArrayList<QuestionBank>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_QUESTIONBANK");
		try {
			while (resultset.next()) {
				questionBank = new QuestionBank();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				questionBank.QUESTIONBANK_ID = resultset
						.getInt("QUESTIONBANK_ID");
				questionBank.QUESTIONBANK_TEXT = resultset
						.getString("QUESTIONBANK_TEXT");
				questionBank.QUESTIONBANK_HINT = resultset
						.getString("QUESTIONBANK_HINT");
				questionBank.QUESTIONBANK_EXPLANATION = resultset
						.getString("QUESTIONBANK_EXPLANATION");
				questionBank.QUESTIONBANK_DIFFICULTYLEVEL = resultset
						.getInt("QUESTIONBANK_DIFFICULTYLEVEL");
				questionBank.QUESTIONBANK_CREATEDBY = resultset
						.getInt("QUESTIONBANK_CREATEDBY");
				questionBank.QUESTIONBANK_MODIFIEDBY = resultset
						.getInt("QUESTIONBANK_MODIFIEDBY");
				questionBank.CSC_QB_IS_PARAMETERIZED = resultset.getString(
						"CSC_QB_IS_PARAMETERIZED").equalsIgnoreCase("f") ? false
						: true;
				questionBank.CSC_QUESTIONBANK_TOPIC_ID = resultset
						.getInt("CSC_QUESTIONBANK_TOPIC_ID");
				try {
					questionBank.QUESTIONBANK_CREATEDDATE = simpleDateFormat
							.parse(resultset
									.getString("QUESTIONBANK_CREATEDDATE"));
					questionBank.QUESTIONBANK_MODIFIEDDATE = simpleDateFormat
							.parse(resultset
									.getString("QUESTIONBANK_MODIFIEDDATE"));
				} catch (Exception e) {
				}
				listQuestionBank.add(questionBank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listQuestionBank;
	}

	// returns answer bank
	// CSC_ANSWERBANK
	public ArrayList<AnswerBank> GetAnswerBank() {
		AnswerBank answerBank = new AnswerBank();
		ArrayList<AnswerBank> listAnswerBank = new ArrayList<AnswerBank>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_ANSWERBANK");
		try {
			while (resultset.next()) {
				answerBank = new AnswerBank();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				answerBank.ANSWERBANK_ID = resultset.getInt("ANSWERBANK_ID");
				answerBank.ANSWERBANK_TEXT = resultset
						.getString("ANSWERBANK_TEXT");
				answerBank.ANSWERBANK_EXPLANATION = resultset
						.getString("ANSWERBANK_EXPLANATION");
				answerBank.ANSWERBANK_CREATEDBY = resultset
						.getInt("ANSWERBANK_CREATEDBY");
				answerBank.ANSWERBANK_MODIFIEDBY = resultset
						.getInt("ANSWERBANK_MODIFIEDBY");
				try {
					answerBank.ANSWERBANK_CREATEDDATE = simpleDateFormat
							.parse(resultset
									.getString("ANSWERBANK_CREATEDDATE"));
					answerBank.ANSWERBANK_MODIFIEDDATE = simpleDateFormat
							.parse(resultset
									.getString("ANSWERBANK_MODIFIEDDATE"));
				} catch (Exception e) {
				}
				listAnswerBank.add(answerBank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listAnswerBank;
	}

	public List<QuestionBank> GetQuestion(int exercise_id) {
		QuestionBank questionBank = new QuestionBank();

		List<QuestionBank> ListQuestions = new LinkedList<QuestionBank>();

		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_QUESTIONBANK where QUESTIONBANK_ID IN (select EA_QUESTION_ID from CSC_EXERCISE_QUESTION where EA_EXERCISE_ID = "
						+ exercise_id + ") ORDER BY DBMS_RANDOM.RANDOM;");
		try {
			if (resultset == null)
				return ListQuestions;
			while (resultset.next()) {
				questionBank = new QuestionBank();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				questionBank.QUESTIONBANK_ID = resultset
						.getInt("QUESTIONBANK_ID");
				questionBank.QUESTIONBANK_TEXT = resultset
						.getString("QUESTIONBANK_TEXT");
				questionBank.QUESTIONBANK_HINT = resultset
						.getString("QUESTIONBANK_HINT");
				questionBank.QUESTIONBANK_EXPLANATION = resultset
						.getString("QUESTIONBANK_EXPLANATION");
				questionBank.QUESTIONBANK_DIFFICULTYLEVEL = resultset
						.getInt("QUESTIONBANK_DIFFICULTYLEVEL");
				questionBank.QUESTIONBANK_CREATEDBY = resultset
						.getInt("QUESTIONBANK_CREATEDBY");
				questionBank.QUESTIONBANK_MODIFIEDBY = resultset
						.getInt("QUESTIONBANK_MODIFIEDBY");
				questionBank.CSC_QB_IS_PARAMETERIZED = resultset
						.getBoolean("CSC_QB_IS_PARAMETERIZED");
				questionBank.CSC_QUESTIONBANK_TOPIC_ID = resultset
						.getInt("CSC_QUESTIONBANK_TOPIC_ID");
				try {
					questionBank.QUESTIONBANK_CREATEDDATE = simpleDateFormat
							.parse(resultset
									.getString("QUESTIONBANK_CREATEDDATE"));
					questionBank.QUESTIONBANK_MODIFIEDDATE = simpleDateFormat
							.parse(resultset
									.getString("QUESTIONBANK_MODIFIEDDATE"));
				} catch (Exception e) {
				}
				ListQuestions.add(questionBank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return ListQuestions;
	}

	public List<AnswerBank> GetCorrectAnswerBank(int Question_id,
			int no_of_correct) {
		AnswerBank answerBank = new AnswerBank();
		List<AnswerBank> listAnswerBank = new LinkedList<AnswerBank>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * From (Select AB.* from CSC_Answerbank AB, CSC_QUESTIONBANK_ANSWERBANK QBAB where AB.ANSWERBANK_ID = QBAB.QABANK_ANSWER_ID and QBAB.QABANK_ISCORRECT = 'T' and QBAB.QABANK_QUESITON_ID = "
						+ Question_id
						+ " ORDER BY DBMS_RANDOM.RANDOM) where ROWNUM <= "
						+ no_of_correct);
		try {
			if (resultset == null) {
				return listAnswerBank;
			}
			while (resultset.next()) {
				answerBank = new AnswerBank();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				answerBank.ANSWERBANK_ID = resultset.getInt("ANSWERBANK_ID");
				answerBank.ANSWERBANK_TEXT = resultset
						.getString("ANSWERBANK_TEXT");
				answerBank.ANSWERBANK_EXPLANATION = resultset
						.getString("ANSWERBANK_EXPLANATION");
				answerBank.ANSWERBANK_CREATEDBY = resultset
						.getInt("ANSWERBANK_CREATEDBY");
				answerBank.ANSWERBANK_MODIFIEDBY = resultset
						.getInt("ANSWERBANK_MODIFIEDBY");
				try {
					answerBank.ANSWERBANK_CREATEDDATE = simpleDateFormat
							.parse(resultset
									.getString("ANSWERBANK_CREATEDDATE"));
					answerBank.ANSWERBANK_MODIFIEDDATE = simpleDateFormat
							.parse(resultset
									.getString("ANSWERBANK_MODIFIEDDATE"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				listAnswerBank.add(answerBank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listAnswerBank;
	}

	public ArrayList<AnswerBank> GetInCorrectAnswerBank(int Question_id,
			int no_of_incorrect) {
		AnswerBank answerBank = new AnswerBank();
		ArrayList<AnswerBank> listAnswerBank = new ArrayList<AnswerBank>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * From (Select AB.* from CSC_Answerbank AB, CSC_QUESTIONBANK_ANSWERBANK QBAB where AB.ANSWERBANK_ID = QBAB.QABANK_ANSWER_ID and QBAB.QABANK_ISCORRECT = 'F' and QBAB.QABANK_QUESITON_ID = "
						+ Question_id
						+ " ORDER BY DBMS_RANDOM.RANDOM) where ROWNUM <= "
						+ no_of_incorrect);
		try {
			if (resultset == null) {
				return listAnswerBank;
			}
			while (resultset.next()) {
				answerBank = new AnswerBank();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				answerBank.ANSWERBANK_ID = resultset.getInt("ANSWERBANK_ID");
				answerBank.ANSWERBANK_TEXT = resultset
						.getString("ANSWERBANK_TEXT");
				answerBank.ANSWERBANK_EXPLANATION = resultset
						.getString("ANSWERBANK_EXPLANATION");
				answerBank.ANSWERBANK_CREATEDBY = resultset
						.getInt("ANSWERBANK_CREATEDBY");
				answerBank.ANSWERBANK_MODIFIEDBY = resultset
						.getInt("ANSWERBANK_MODIFIEDBY");
				try {
					answerBank.ANSWERBANK_CREATEDDATE = simpleDateFormat
							.parse(resultset
									.getString("ANSWERBANK_CREATEDDATE"));
					answerBank.ANSWERBANK_MODIFIEDDATE = simpleDateFormat
							.parse(resultset
									.getString("ANSWERBANK_MODIFIEDDATE"));
				} catch (Exception e) {
				}
				listAnswerBank.add(answerBank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listAnswerBank;
	}

	// returns question answer bank
	public ArrayList<QuestionBank_AnswerBank> GetQuestionAnswerBank() {
		QuestionBank_AnswerBank questionAnswerBank = new QuestionBank_AnswerBank();
		ArrayList<QuestionBank_AnswerBank> listQABank = new ArrayList<QuestionBank_AnswerBank>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_QUESTIONBANK_ANSWERBANK");
		try {
			while (resultset.next()) {
				questionAnswerBank = new QuestionBank_AnswerBank();
				questionAnswerBank.QABANK_QUESITON_ID = resultset
						.getInt("QABANK_QUESITON_ID");
				questionAnswerBank.QABANK_ANSWER_ID = resultset
						.getInt("QABANK_ANSWER_ID");
				questionAnswerBank.QABANK_ISCORRECT = resultset
						.getBoolean("ANSWERBANK_EXPLANATION");
				listQABank.add(questionAnswerBank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listQABank;
	}

	// returns course list.
	public ArrayList<Course> GetCourse() {

		Course course = new Course();
		ArrayList<Course> listCourse = new ArrayList<Course>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb.GetResultSet("Select * from CSC_COURSE");
		try {
			while (resultset.next()) {
				course = new Course();

				course.CSC_COURSE_Course_ID = resultset
						.getInt("CSC_COURSE_Course_ID");
				course.CSC_COURSE_Course_Name = resultset
						.getString("CSC_COURSE_Course_Name");
				course.CSC_COURSE_token = resultset
						.getString("CSC_COURSE_token");
				course.CSC_COURSE_Max_Enroll_No = resultset
						.getInt("CSC_COURSE_Max_Enroll_No");
				course.CSC_COURSE_Number_Of_Students = resultset
						.getInt("CSC_COURSE_Number_Of_Students");
				try {
					course.CSC_COURSE_StartDate = simpleDateFormat
							.parse(resultset.getString("CSC_COURSE_StartDate"));
					course.CSC_COURSE_EndDate = simpleDateFormat
							.parse(resultset.getString("CSC_COURSE_EndDate"));
				} catch (Exception e) {
				}
				listCourse.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listCourse;

	}

	public ArrayList<Course> GetAllCourseForUser(User user) {
		Course course = new Course();
		ArrayList<Course> listCourse = new ArrayList<Course>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		oracleDb.OpenConnection();
		String query = "SELECT * FROM CSC_COURSE CCO "
				+ "inner join CSC_CLASS CCS on CCO.CSC_COURSE_COURSE_ID=CCS.CSC_CLASS_COURSE_ID "
				+ "inner join csc_user_role CUR on CUR.CSC_U_R_CLASS_SURR_KEY=CCS.CSC_CLASS_COURSE_ID "
				+ "WHERE CUR.User_ROLE_USER_ID=" + user.UserId;
		ResultSet resultset = oracleDb.GetResultSet(query);
		try {
			while (resultset.next()) {
				course = new Course();

				course.CSC_COURSE_Course_ID = resultset
						.getInt("CSC_COURSE_Course_ID");
				course.CSC_COURSE_Course_Name = resultset
						.getString("CSC_COURSE_Course_Name");
				course.CSC_COURSE_token = resultset
						.getString("CSC_COURSE_token");
				course.CSC_COURSE_Max_Enroll_No = resultset
						.getInt("CSC_COURSE_Max_Enroll_No");
				course.CSC_COURSE_Number_Of_Students = resultset
						.getInt("CSC_COURSE_Number_Of_Students");
				try {
					course.CSC_COURSE_StartDate = simpleDateFormat
							.parse(resultset.getString("CSC_COURSE_StartDate"));
					course.CSC_COURSE_EndDate = simpleDateFormat
							.parse(resultset.getString("CSC_COURSE_EndDate"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				listCourse.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listCourse;
	}

	public ArrayList<Course> GetCourseForStudent(User user) {

		int roleId = 0;
		for (UserRole userRole : user.UserRoles) {
			if (userRole.Roles.Role_Name.equalsIgnoreCase("student")) {
				roleId = userRole.Roles.Role_ID;
				break;
			}
		}
		Course course = new Course();
		ArrayList<Course> listCourse = new ArrayList<Course>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		oracleDb.OpenConnection();
		String query = "select * from csc_course where CSC_COURSE_COURSE_ID IN (select CSC_CLASS_COURSE_ID from CSC_CLASS where CSC_CLASS_SURR_KEY IN(select CSC_U_R_CLASS_SURR_KEY from csc_user_role where USER_ROLE_USER_ID = "
				+ user.UserId + " and USER_ROLE_ROLE_ID =" + roleId + "))";
		ResultSet resultset = oracleDb.GetResultSet(query);
		try {
			while (resultset.next()) {
				course = new Course();

				course.CSC_COURSE_Course_ID = resultset
						.getInt("CSC_COURSE_Course_ID");
				course.CSC_COURSE_Course_Name = resultset
						.getString("CSC_COURSE_Course_Name");
				course.CSC_COURSE_token = resultset
						.getString("CSC_COURSE_token");
				course.CSC_COURSE_Max_Enroll_No = resultset
						.getInt("CSC_COURSE_Max_Enroll_No");
				course.CSC_COURSE_Number_Of_Students = resultset
						.getInt("CSC_COURSE_Number_Of_Students");
				try {
					course.CSC_COURSE_StartDate = simpleDateFormat
							.parse(resultset.getString("CSC_COURSE_StartDate"));
					course.CSC_COURSE_EndDate = simpleDateFormat
							.parse(resultset.getString("CSC_COURSE_EndDate"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				listCourse.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listCourse;

	}

	public ArrayList<Course> GetCourseForToken(String tokenId) {
		Course course = new Course();
		ArrayList<Course> listCourse = new ArrayList<Course>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_COURSE WHERE CSC_COURSE_token='"
						+ tokenId + "'");
		try {
			while (resultset.next()) {
				course = new Course();

				course.CSC_COURSE_Course_ID = resultset
						.getInt("CSC_COURSE_COURSE_ID");
				course.CSC_COURSE_Course_Name = resultset
						.getString("CSC_COURSE_COURSE_NAME");
				course.CSC_COURSE_token = resultset
						.getString("CSC_COURSE_TOKEN");
				course.CSC_COURSE_Max_Enroll_No = resultset
						.getInt("CSC_COURSE_Max_Enroll_No");
				course.CSC_COURSE_Number_Of_Students = resultset
						.getInt("CSC_COURSE_Number_Of_Students");
				try {

					course.CSC_COURSE_StartDate = simpleDateFormat
							.parse(resultset.getString("CSC_COURSE_STARTDATE"));
					course.CSC_COURSE_EndDate = simpleDateFormat
							.parse(resultset.getString("CSC_COURSE_ENDDATE"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				listCourse.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listCourse;
	}

	public boolean AddStudentToCourse(String token, User user) {
		ArrayList<Course> listCourse = GetCourseForToken(token);
		for (Course course : listCourse) {
			if (course.CSC_COURSE_Max_Enroll_No > course.CSC_COURSE_Number_Of_Students) {
				if (user.UserId != 0) {
					try {
						ArrayList<Object> param = new ArrayList<Object>();
						param.add(user.UserId);
						param.add(token);
						boolean returnvalue = oracleDb
								.ExecuteStoredProcedure2Param(
										"CSC_InsertCourseForStudent", param);
						oracleDb.CloseConnection();
						return returnvalue;
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else
					return false;
			} else
				return false;
		}
		return false;

	}

	public ArrayList<CSCClass> GetClass() {

		CSCClass cscClass = new CSCClass();
		ArrayList<CSCClass> listClass = new ArrayList<CSCClass>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb.GetResultSet("Select * from CSC_CLASS");
		try {
			while (resultset.next()) {
				cscClass = new CSCClass();

				cscClass.CSC_CLASS_SURR_KEY = resultset
						.getInt("CSC_CLASS_SURR_KEY");
				cscClass.CSC_CLASS_CLASS_ID = resultset
						.getInt("CSC_CLASS_CLASS_ID");
				cscClass.CSC_CLASS_COURSE_ID = resultset
						.getInt("CSC_CLASS_COURSE_ID");
				listClass.add(cscClass);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listClass;
	}

	public ArrayList<Level> GetLevel() {
		Level level = new Level();
		ArrayList<Level> listLevel = new ArrayList<Level>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb.GetResultSet("Select * from CSC_Level");
		try {
			while (resultset.next()) {
				level = new Level();

				level.CSC_LEVEL_LEVEL_ID = resultset
						.getInt("CSC_LEVEL_LEVEL_ID");
				level.CSC_LEVEL_LEVEL_TYPE = resultset
						.getString("CSC_LEVEL_LEVEL_TYPE ");
				listLevel.add(level);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listLevel;
	}

	public ArrayList<TextBook> GetTextBook() {
		TextBook textBook = new TextBook();
		ArrayList<TextBook> listTextBook = new ArrayList<TextBook>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_TEXTBOOK");
		try {
			while (resultset.next()) {
				textBook = new TextBook();

				textBook.CSC_TEXTBOOK_ISBN = resultset
						.getString("CSC_TEXTBOOK_ISBN");
				textBook.CSC_TEXTBOOK_Title = resultset
						.getString("CSC_TEXTBOOK_Title");
				textBook.CSC_TEXTBOOK_Author = resultset
						.getString("CSC_TEXTBOOK_Author");
				listTextBook.add(textBook);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listTextBook;
	}

	// returns chapter
	public ArrayList<Chapter> GetChapter() {
		Chapter chapter = new Chapter();
		ArrayList<Chapter> listChapter = new ArrayList<Chapter>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_CHAPTER");
		try {
			while (resultset.next()) {
				chapter = new Chapter();

				chapter.CSC_CHAPTER_Chapter_Surr_Key = resultset
						.getInt("CSC_CHAPTER_Chapter_Surr_Key");
				chapter.CSC_CHAPTER_Chapter_Id = resultset
						.getInt("CSC_CHAPTER_Chapter_Id");
				chapter.CSC_CHAPTER_Title = resultset
						.getString("CSC_CHAPTER_Title");
				chapter.CSC_CHAPTER_ISBN = resultset
						.getString("CSC_CHAPTER_ISBN");
				listChapter.add(chapter);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listChapter;
	}

	// returns Section
	public ArrayList<Section> GetSection() {
		Section section = new Section();
		ArrayList<Section> listSection = new ArrayList<Section>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_SECTION");
		try {
			while (resultset.next()) {
				section = new Section();

				section.CSC_SECTION_SECTION_SURR_KEY = resultset
						.getInt("CSC_SECTION_SECTION_SURR_KEY");
				section.CSC_SECTION_SECTION_ID = resultset
						.getInt("CSC_SECTION_SECTION_ID");
				section.CSC_SECTION_SUPER_SECTION_ID = resultset
						.getInt("CSC_SECTION_SUPER_SECTION_ID");
				section.CSC_SECTION_TITLE = resultset
						.getString("CSC_SECTION_TITLE");
				section.CSC_SECTION_CONTENT = resultset
						.getString("CSC_SECTION_CONTENT");
				section.CSC_SECTION_Chapter_Surr_Key = resultset
						.getInt("CSC_SECTION_Chapter_Surr_Key ");
				listSection.add(section);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listSection;
	}

	// returns CourseTextbook List
	public ArrayList<CourseTextbook> GetCourseTextbook() {
		CourseTextbook courseTextbook = new CourseTextbook();
		ArrayList<CourseTextbook> listCourseTextbook = new ArrayList<CourseTextbook>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_COURSE_TEXTBOOK");
		try {
			while (resultset.next()) {
				courseTextbook = new CourseTextbook();

				courseTextbook.CSC_COURSE_TEXTBOOK_SURR_KEY = resultset
						.getInt("CSC_COURSE_TEXTBOOK_SURR_KEY");
				courseTextbook.CSC_CRS_TXTBK_CSC_CRS_CRS_ID = resultset
						.getInt("CSC_CRS_TXTBK_CSC_CRS_CRS_ID");
				courseTextbook.CSC_CRs_TXTBK_CSC_TXTBK_ISBN = resultset
						.getString("CSC_CRs_TXTBK_CSC_TXTBK_ISBN");
				listCourseTextbook.add(courseTextbook);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listCourseTextbook;
	}

	public ArrayList<ScoringType> GetScoringType() {
		ScoringType scoringType = new ScoringType();
		ArrayList<ScoringType> listScoringType = new ArrayList<ScoringType>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_SCORINGTYPE");
		try {
			while (resultset.next()) {
				scoringType = new ScoringType();

				scoringType.SCORINGTYPE_ID = resultset.getInt("SCORINGTYPE_ID");
				scoringType.SCORINGTYPE_DESCRIPTION = resultset
						.getString("SCORINGTYPE_DESCRIPTION");
				scoringType.SCORINGTYPE_LOGIC = resultset
						.getString("SCORINGTYPE_LOGIC");
				listScoringType.add(scoringType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listScoringType;
	}

	public ArrayList<Exercise> GetExercise() {
		Exercise exercise = new Exercise();
		ArrayList<Exercise> listExercise = new ArrayList<Exercise>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_EXERCISE");
		try {
			while (resultset.next()) {
				exercise = new Exercise();

				exercise.EXERCISE_ID = resultset.getInt("EXERCISE_ID");
				exercise.EXERCISE_NAME = resultset.getString("EXERCISE_NAME");
				exercise.EXERCISE_COURSE = resultset.getInt("EXERCISE_COURSE");
				exercise.EXERCISE_NAME = resultset.getString("EXERCISE_NAME");
				exercise.EXERCISE_DIFFICULTY_RANGE1 = resultset
						.getInt("EXERCISE_DIFFICULTY_RANGE1");
				exercise.EXERCISE_DIFFICULTY_RANGE2 = resultset
						.getInt("EXERCISE_DIFFICULTY_RANGE2");
				exercise.EXERCISE_RETRYLIMIT = resultset
						.getInt("EXERCISE_RETRYLIMIT");
				exercise.EXERCISE_CORRECTPT = resultset
						.getInt("EXERCISE_CORRECTPT");
				exercise.EXERCISE_PENALTYPT = resultset
						.getInt("EXERCISE_PENALTYPT");
				exercise.EXERCISE_SCORINGTYPE = resultset
						.getInt("EXERCISE_SCORINGTYPE");
				exercise.EXERCISE_CREATEDBY = resultset
						.getInt("EXERCISE_CREATEDBY");
				exercise.EXERCISE_MODIFIEDBY = resultset
						.getInt("EXERCISE_MODIFIEDBY");

				try {
					exercise.EXERCISE_STARTDATE = simpleDateFormat
							.parse(resultset.getString("EXERCISE_STARTDATE"));
					exercise.EXERCISE_ENDDATE = simpleDateFormat
							.parse(resultset.getString("EXERCISE_ENDDATE"));
					exercise.EXERCISE_LASTMODIFIEDDATE = simpleDateFormat
							.parse(resultset
									.getString("EXERCISE_LASTMODIFIEDDATE"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				listExercise.add(exercise);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listExercise;
	}

	public int InsertUserAttempSubmit(int exercise_id, double score,
			String IsSubmitted) {
		ArrayList<Object> param = new ArrayList<Object>();
		User user = LocalSession.GetCurrentUser();
		try {
			param.add(user.UserId);
			param.add(exercise_id);
			param.add(score);
			param.add(IsSubmitted);
			int returnvalue = oracleDb.ExecuteStoredProcedure4ParamOut(
					"CSC_InsertUserAttempSubmit", param);
			oracleDb.CloseConnection();
			return returnvalue;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int UpdateUserAttempSubmit(int exercise_id, double score,
			String IsSubmitted) {
		ArrayList<Object> param = new ArrayList<Object>();
		User user = LocalSession.GetCurrentUser();
		try {
			param.add(user.UserId);
			param.add(exercise_id);
			param.add(score);
			param.add(IsSubmitted);
			int returnvalue = oracleDb.ExecuteStoredProcedure4ParamOut(
					"CSC_InsertUserAttempt_final", param);
			oracleDb.CloseConnection();
			return returnvalue;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public ArrayList<Exercise> GetActiveExerciseForCourse(int course_id) {
		Exercise exercise = new Exercise();
		ArrayList<Exercise> listExercise = new ArrayList<Exercise>();
		User user = LocalSession.GetCurrentUser();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		oracleDb.OpenConnection();

		int uid = user.UserId;
		String query = "Select EX.* from csc_exercise EX where EX.EXERCISE_RETRYLIMIT > ANY (Select count(ATTEMP_ID) From csc_user_attempt UA1 where UA1.UA_EXERCISE_ID = EX.EXERCISE_ID and UA1.UA_USER_ID = "
				+ uid
				+ ") and TRUNC(EX.EXERCISE_STARTDATE) <= TRUNC(SYSDATE) AND TRUNC(EX.EXERCISE_ENDDATE) >= TRUNC(SYSDATE) AND EXERCISE_COURSE ="
				+ course_id;
		ResultSet resultset = oracleDb.GetResultSet(query);
		try {
			if (resultset == null) {
				return listExercise;
			}
			while (resultset.next()) {
				exercise = new Exercise();

				exercise.EXERCISE_ID = resultset.getInt("EXERCISE_ID");
				exercise.EXERCISE_NAME = resultset.getString("EXERCISE_NAME");
				exercise.EXERCISE_COURSE = resultset.getInt("EXERCISE_COURSE");
				exercise.EXERCISE_NAME = resultset.getString("EXERCISE_NAME");
				exercise.EXERCISE_DIFFICULTY_RANGE1 = resultset
						.getInt("EXERCISE_DIFFICULTY_RANGE1");
				exercise.EXERCISE_DIFFICULTY_RANGE2 = resultset
						.getInt("EXERCISE_DIFFICULTY_RANGE2");
				exercise.EXERCISE_RETRYLIMIT = resultset
						.getInt("EXERCISE_RETRYLIMIT");
				exercise.EXERCISE_CORRECTPT = resultset
						.getInt("EXERCISE_CORRECTPT");
				exercise.EXERCISE_PENALTYPT = resultset
						.getInt("EXERCISE_PENALTYPT");
				exercise.EXERCISE_SCORINGTYPE = resultset
						.getInt("EXERCISE_SCORINGTYPE");
				exercise.EXERCISE_CREATEDBY = resultset
						.getInt("EXERCISE_CREATEDBY");
				exercise.EXERCISE_MODIFIEDBY = resultset
						.getInt("EXERCISE_MODIFIEDBY");

				try {
					exercise.EXERCISE_STARTDATE = simpleDateFormat
							.parse(resultset.getString("EXERCISE_STARTDATE"));
					exercise.EXERCISE_ENDDATE = simpleDateFormat
							.parse(resultset.getString("EXERCISE_ENDDATE"));
					exercise.EXERCISE_LASTMODIFIEDDATE = simpleDateFormat
							.parse(resultset
									.getString("EXERCISE_LASTMODIFIEDDATE"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				listExercise.add(exercise);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listExercise;
	}

	public Exercise GetExercise(int exerciseId) {
		Exercise exercise = new Exercise();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_EXERCISE where EXERCISE_ID="
						+ exerciseId);
		try {
			while (resultset.next()) {
				exercise = new Exercise();

				exercise.EXERCISE_ID = resultset.getInt("EXERCISE_ID");
				exercise.EXERCISE_NAME = resultset.getString("EXERCISE_NAME");
				exercise.EXERCISE_COURSE = resultset.getInt("EXERCISE_COURSE");
				exercise.EXERCISE_NAME = resultset.getString("EXERCISE_NAME");
				exercise.EXERCISE_DIFFICULTY_RANGE1 = resultset
						.getInt("EXERCISE_DIFFICULTY_RANGE1");
				exercise.EXERCISE_DIFFICULTY_RANGE2 = resultset
						.getInt("EXERCISE_DIFFICULTY_RANGE2");
				exercise.EXERCISE_RETRYLIMIT = resultset
						.getInt("EXERCISE_RETRYLIMIT");
				exercise.EXERCISE_CORRECTPT = resultset
						.getInt("EXERCISE_CORRECTPT");
				exercise.EXERCISE_PENALTYPT = resultset
						.getInt("EXERCISE_PENALTYPT");
				exercise.EXERCISE_SCORINGTYPE = resultset
						.getInt("EXERCISE_SCORINGTYPE");
				exercise.EXERCISE_CREATEDBY = resultset
						.getInt("EXERCISE_CREATEDBY");
				exercise.EXERCISE_MODIFIEDBY = resultset
						.getInt("EXERCISE_MODIFIEDBY");

				try {
					exercise.EXERCISE_STARTDATE = simpleDateFormat
							.parse(resultset.getString("EXERCISE_STARTDATE"));
					exercise.EXERCISE_ENDDATE = simpleDateFormat
							.parse(resultset.getString("EXERCISE_ENDDATE"));
					exercise.EXERCISE_LASTMODIFIEDDATE = simpleDateFormat
							.parse(resultset
									.getString("EXERCISE_LASTMODIFIEDDATE"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return exercise;
	}

	public boolean UpdateExerciseDetails(Exercise exercise) {
		oracleDb.OpenConnection();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		String query = "UPDATE CSC_EXERCISE SET " + "EXERCISE_NAME='"
				+ exercise.EXERCISE_NAME
				+ "',EXERCISE_COURSE="
				+ exercise.EXERCISE_COURSE
				+ ",EXERCISE_DIFFICULTY_RANGE1="
				+ exercise.EXERCISE_DIFFICULTY_RANGE1
				+ ",EXERCISE_DIFFICULTY_RANGE2="
				+ exercise.EXERCISE_DIFFICULTY_RANGE2
				+ ",EXERCISE_RETRYLIMIT="
				+ exercise.EXERCISE_RETRYLIMIT
				+ ",EXERCISE_CORRECTPT="
				+ exercise.EXERCISE_CORRECTPT
				+ ",EXERCISE_PENALTYPT="
				+ exercise.EXERCISE_PENALTYPT
				+ ",EXERCISE_SCORINGTYPE="
				+ exercise.EXERCISE_SCORINGTYPE
				+ ",EXERCISE_CREATEDBY="
				+ exercise.EXERCISE_CREATEDBY
				+ ",EXERCISE_MODIFIEDBY="
				+ exercise.EXERCISE_CREATEDBY
				+ ",EXERCISE_STARTDATE=TO_DATE('"
				+ simpleDateFormat.format(exercise.EXERCISE_STARTDATE)
				+ "','YYYY-MM-DD'),EXERCISE_ENDDATE=TO_DATE('"
				+ simpleDateFormat.format(exercise.EXERCISE_ENDDATE)
				+ "','YYYY-MM-DD'),EXERCISE_LASTMODIFIEDDATE=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE EXERCISE_ID="
				+ exercise.EXERCISE_ID;
		try {
			return oracleDb.InsertQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public int InsertExerciseDetails(Exercise exercise) {

		oracleDb.OpenConnection();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		String query = "insert into CSC_EXERCISE(EXERCISE_ID,EXERCISE_NAME,EXERCISE_COURSE,EXERCISE_DIFFICULTY_RANGE1,EXERCISE_DIFFICULTY_RANGE2,EXERCISE_RETRYLIMIT,EXERCISE_CORRECTPT,EXERCISE_PENALTYPT,EXERCISE_SCORINGTYPE,EXERCISE_CREATEDBY,EXERCISE_MODIFIEDBY,EXERCISE_STARTDATE,EXERCISE_ENDDATE,EXERCISE_LASTMODIFIEDDATE)";
		query = query + "Values(CSC_EXERCISE_sequence.NextVal,'"
				+ exercise.EXERCISE_NAME + "','" + exercise.EXERCISE_COURSE
				+ "','" + exercise.EXERCISE_DIFFICULTY_RANGE1 + "','"
				+ exercise.EXERCISE_DIFFICULTY_RANGE2 + "','";
		query = query + exercise.EXERCISE_RETRYLIMIT + "','"
				+ exercise.EXERCISE_CORRECTPT + "','"
				+ exercise.EXERCISE_PENALTYPT + "','"
				+ exercise.EXERCISE_SCORINGTYPE + "','"
				+ exercise.EXERCISE_CREATEDBY + "','"
				+ exercise.EXERCISE_CREATEDBY + "',To_Date('";
		query = query + simpleDateFormat.format(exercise.EXERCISE_STARTDATE)
				+ "','yyyy-mm-dd'),To_Date('"
				+ simpleDateFormat.format(exercise.EXERCISE_ENDDATE)
				+ "','yyyy-mm-dd'),TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))";
		int retval = 0;
		try {
			oracleDb.InsertQuery(query);

			ResultSet resultset = oracleDb
					.GetResultSet("select Max(EXERCISE_ID) as EXERCISE_ID  from csc_exercise ");

			while (resultset.next()) {
				retval = resultset.getInt("EXERCISE_ID");
				return retval;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retval;
	}

	public boolean InsertExerciseQuestion(Exercise exercise,
			ArrayList<QuestionBank> listSelectedQuestion) {
		// delete all the old questions
		{
			String query = "DELETE FROM CSC_EXERCISE_QUESTION where EA_EXERCISE_ID="
					+ exercise.EXERCISE_ID;
			ResultSet resultset = oracleDb.GetResultSet(query);
		}
		for (QuestionBank questionBank : listSelectedQuestion) {
			String query = "INSERT INTO CSC_EXERCISE_QUESTION (EA_ID,EA_EXERCISE_ID,EA_QUESTION_ID,EA_QUES_IS_PARM) values(";
			String cSC_QB_IS_PARAMETERIZED = questionBank.CSC_QB_IS_PARAMETERIZED == false ? "F"
					: "T";
			query = query + "CSC_EXERCISE_Question_sequence.NextVal,"
					+ exercise.EXERCISE_ID + "," + questionBank.QUESTIONBANK_ID
					+ ",'" + cSC_QB_IS_PARAMETERIZED + "'";
			query = query + ")";
			oracleDb.InsertQuery(query);
		}
		return false;
	}

	public ArrayList<Topic> GetTopic() {
		Topic topic = new Topic();
		ArrayList<Topic> listTopic = new ArrayList<Topic>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb.GetResultSet("Select * from CSC_TOPIC");
		try {
			while (resultset.next()) {
				topic = new Topic();

				topic.TOPIC_ID = resultset.getInt("TOPIC_ID");
				topic.TOPIC_KEYWORD = resultset.getString("TOPIC_KEYWORD");
				topic.TOPIC_CREATEDBY = resultset.getInt("TOPIC_CREATEDBY");
				try {
					topic.TOPIC_CREATEDDATE = simpleDateFormat.parse(resultset
							.getString("TOPIC_CREATEDDATE"));
				} catch (Exception e) {
				}
				listTopic.add(topic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listTopic;
	}

	public ArrayList<Topic> GetTopicByCourseId(Course course) {
		Topic topic = new Topic();
		ArrayList<Topic> listTopic = new ArrayList<Topic>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		oracleDb.OpenConnection();
		String query = "Select * from CSC_TOPIC where TOPIC_ID IN (select CSC_COURSE_TOPIC_TOPIC_ID from CSC_COURSE_TOPIC where CSC_COURSE_TOPIC_COURSE_ID = "
				+ course.CSC_COURSE_Course_ID + ")";
		ResultSet resultset = oracleDb.GetResultSet(query);
		try {
			while (resultset.next()) {
				topic = new Topic();

				topic.TOPIC_ID = resultset.getInt("TOPIC_ID");
				topic.TOPIC_KEYWORD = resultset.getString("TOPIC_KEYWORD");
				topic.TOPIC_CREATEDBY = resultset.getInt("TOPIC_CREATEDBY");
				try {
					topic.TOPIC_CREATEDDATE = simpleDateFormat.parse(resultset
							.getString("TOPIC_CREATEDDATE"));
				} catch (Exception e) {
				}
				listTopic.add(topic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listTopic;
	}

	public ArrayList<QuestionBank_Topic> GetQuestionBankTopic() {
		QuestionBank_Topic questionBankTopic = new QuestionBank_Topic();
		ArrayList<QuestionBank_Topic> listQuestionBankTopic = new ArrayList<QuestionBank_Topic>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_QUESTIONBANK_TOPIC");
		try {
			while (resultset.next()) {
				questionBankTopic = new QuestionBank_Topic();

				questionBankTopic.QT_TOPIC_ID = resultset.getInt("QT_TOPIC_ID");
				questionBankTopic.QT_QUESTIONBANK_ID = resultset
						.getInt("QT_QUESTIONBANK_ID");
				listQuestionBankTopic.add(questionBankTopic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listQuestionBankTopic;
	}

	public ArrayList<QuestionBank> GetQuestionByTopic(Topic topic) {
		QuestionBank questionBank = new QuestionBank();
		ArrayList<QuestionBank> listQuestionBank = new ArrayList<QuestionBank>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("select * from CSC_QUESTIONBANK where CSC_QUESTIONBANK_TOPIC_ID = "
						+ topic.TOPIC_ID);
		try {
			while (resultset.next()) {
				questionBank = new QuestionBank();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				questionBank.QUESTIONBANK_ID = resultset
						.getInt("QUESTIONBANK_ID");
				questionBank.QUESTIONBANK_TEXT = resultset
						.getString("QUESTIONBANK_TEXT");
				questionBank.QUESTIONBANK_HINT = resultset
						.getString("QUESTIONBANK_HINT");
				questionBank.QUESTIONBANK_EXPLANATION = resultset
						.getString("QUESTIONBANK_EXPLANATION");
				questionBank.QUESTIONBANK_DIFFICULTYLEVEL = resultset
						.getInt("QUESTIONBANK_DIFFICULTYLEVEL");
				questionBank.QUESTIONBANK_CREATEDBY = resultset
						.getInt("QUESTIONBANK_CREATEDBY");
				questionBank.QUESTIONBANK_MODIFIEDBY = resultset
						.getInt("QUESTIONBANK_MODIFIEDBY");
				if (resultset.getString("CSC_QB_IS_PARAMETERIZED")
						.equalsIgnoreCase("f"))
					questionBank.CSC_QB_IS_PARAMETERIZED = false;
				else
					questionBank.CSC_QB_IS_PARAMETERIZED = true;
				questionBank.CSC_QUESTIONBANK_TOPIC_ID = resultset
						.getInt("CSC_QUESTIONBANK_TOPIC_ID");
				try {
					questionBank.QUESTIONBANK_CREATEDDATE = simpleDateFormat
							.parse(resultset
									.getString("QUESTIONBANK_CREATEDDATE"));
					questionBank.QUESTIONBANK_MODIFIEDDATE = simpleDateFormat
							.parse(resultset
									.getString("QUESTIONBANK_MODIFIEDDATE"));
				} catch (Exception e) {
				}
				listQuestionBank.add(questionBank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listQuestionBank;
	}

	public ArrayList<ExerciseQuestion> GetExerciseQuestion() {
		ExerciseQuestion exerciseQuestion = new ExerciseQuestion();
		ArrayList<ExerciseQuestion> listExerciseQuestion = new ArrayList<ExerciseQuestion>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_EXERCISE_QUESTION");
		try {
			while (resultset.next()) {
				exerciseQuestion = new ExerciseQuestion();

				exerciseQuestion.EA_ID = resultset.getInt("EA_ID");
				exerciseQuestion.EA_EXERCISE_ID = resultset
						.getInt("EA_EXERCISE_ID");
				exerciseQuestion.EA_QUESTION_ID = resultset
						.getInt("EA_QUESTION_ID");
				exerciseQuestion.EA_QUES_IS_PARM = resultset
						.getBoolean("EA_QUES_IS_PARM");
				listExerciseQuestion.add(exerciseQuestion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listExerciseQuestion;
	}

	public ArrayList<QuestionBank> GetQuestionBankForExerciseId(
			int EA_EXERCISE_ID) {
		QuestionBank questionBank = new QuestionBank();
		ArrayList<QuestionBank> listQuestionBank = new ArrayList<QuestionBank>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from  CSC_QuestionBank where QUESTIONBANK_ID in(select EA_QUESTION_ID from CSC_EXERCISE_QUESTION where EA_EXERCISE_ID="
						+ EA_EXERCISE_ID + ")");
		try {
			while (resultset.next()) {
				questionBank = new QuestionBank();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				questionBank.QUESTIONBANK_ID = resultset
						.getInt("QUESTIONBANK_ID");
				questionBank.QUESTIONBANK_TEXT = resultset
						.getString("QUESTIONBANK_TEXT");
				questionBank.QUESTIONBANK_HINT = resultset
						.getString("QUESTIONBANK_HINT");
				questionBank.QUESTIONBANK_EXPLANATION = resultset
						.getString("QUESTIONBANK_EXPLANATION");
				questionBank.QUESTIONBANK_DIFFICULTYLEVEL = resultset
						.getInt("QUESTIONBANK_DIFFICULTYLEVEL");
				questionBank.QUESTIONBANK_CREATEDBY = resultset
						.getInt("QUESTIONBANK_CREATEDBY");
				questionBank.QUESTIONBANK_MODIFIEDBY = resultset
						.getInt("QUESTIONBANK_MODIFIEDBY");
				questionBank.CSC_QB_IS_PARAMETERIZED = resultset.getString(
						"CSC_QB_IS_PARAMETERIZED").equalsIgnoreCase("f") ? false
						: true;
				questionBank.CSC_QUESTIONBANK_TOPIC_ID = resultset
						.getInt("CSC_QUESTIONBANK_TOPIC_ID");
				try {
					questionBank.QUESTIONBANK_CREATEDDATE = simpleDateFormat
							.parse(resultset
									.getString("QUESTIONBANK_CREATEDDATE"));
					questionBank.QUESTIONBANK_MODIFIEDDATE = simpleDateFormat
							.parse(resultset
									.getString("QUESTIONBANK_MODIFIEDDATE"));
				} catch (Exception e) {

				}
				listQuestionBank.add(questionBank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listQuestionBank;
	}

	public ArrayList<UserAttempt> GetUserAttempt() {
		UserAttempt userAttempt = new UserAttempt();
		ArrayList<UserAttempt> listUserAttempt = new ArrayList<UserAttempt>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_USER_ATTEMPT");
		try {
			while (resultset.next()) {
				userAttempt = new UserAttempt();

				userAttempt.UA_ID = resultset.getInt("UA_ID");
				userAttempt.UA_USER_ID = resultset.getInt("UA_USER_ID");
				userAttempt.UA_EXERCISE_ID = resultset.getInt("UA_EXERCISE_ID");
				userAttempt.UA_SUBMITTED = resultset.getBoolean("UA_SUBMITTED");
				userAttempt.UA_SCORE = resultset.getDouble("UA_SCORE");
				try {
					userAttempt.UA_STARTATTEMPT_DATE = simpleDateFormat
							.parse(resultset.getString("UA_STARTATTEMPT_DATE"));
					userAttempt.UA_LASTATTEMPT_DATE = simpleDateFormat
							.parse(resultset.getString("UA_LASTATTEMPT_DATE"));
					userAttempt.UA_STARTATTEMPT_DATE = simpleDateFormat
							.parse(resultset.getString("UA_STARTATTEMPT_DATE"));
				} catch (Exception e) {
				}
				listUserAttempt.add(userAttempt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listUserAttempt;
	}

	public ArrayList<UserAttempt> GetUserAttemptFromCourse(int course_id) {

		User user = LocalSession.GetCurrentUser();
		UserAttempt userAttempt = new UserAttempt();
		ArrayList<UserAttempt> listUserAttempt = new ArrayList<UserAttempt>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("select UT.* from CSC_USER_ATTEMPT UT,CSC_EXERCISE EX where UT.UA_EXERCISE_ID = EX.EXERCISE_ID and EX.EXERCISE_COURSE = "
						+ course_id
						+ " and UT.UA_SUBMITTED = 'T'and UT.UA_USER_ID = "
						+ user.UserId);
		try {
			if (resultset == null) {
				return listUserAttempt;
			}
			while (resultset.next()) {
				userAttempt = new UserAttempt();

				userAttempt.UA_ID = resultset.getInt("UA_ID");
				userAttempt.UA_USER_ID = resultset.getInt("UA_USER_ID");
				userAttempt.UA_EXERCISE_ID = resultset.getInt("UA_EXERCISE_ID");
				String submit = resultset.getString("UA_SUBMITTED");
				if ("T".equalsIgnoreCase(submit)) {
					userAttempt.UA_SUBMITTED = true;
				} else {
					userAttempt.UA_SUBMITTED = false;
				}
				userAttempt.UA_SCORE = resultset.getDouble("UA_SCORE");
				try {
					userAttempt.UA_STARTATTEMPT_DATE = simpleDateFormat
							.parse(resultset.getString("UA_STARTATTEMPT_DATE"));
					userAttempt.UA_LASTATTEMPT_DATE = simpleDateFormat
							.parse(resultset.getString("UA_LASTATTEMPT_DATE"));
					userAttempt.UA_STARTATTEMPT_DATE = simpleDateFormat
							.parse(resultset.getString("UA_STARTATTEMPT_DATE"));
				} catch (Exception e) {
				}
				listUserAttempt.add(userAttempt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listUserAttempt;
	}

	public ArrayList<UserAttemptExercise> GetUserAttemptExercise() {
		UserAttemptExercise userAttemptExercise = new UserAttemptExercise();
		ArrayList<UserAttemptExercise> listUserAttemptExercise = new ArrayList<UserAttemptExercise>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_USERATTEMPT_EXERCISE");
		try {
			while (resultset.next()) {
				userAttemptExercise = new UserAttemptExercise();

				userAttemptExercise.UE_UA_ID = resultset.getInt("UE_UA_ID");
				userAttemptExercise.UE_QUESTION_ID = resultset
						.getInt("UE_QUESTION_ID");
				userAttemptExercise.UE_ANSWER_ID = resultset
						.getInt("UE_ANSWER_ID");
				userAttemptExercise.UE_ISSELECTED = resultset
						.getBoolean("UE_ISSELECTED");
				listUserAttemptExercise.add(userAttemptExercise);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listUserAttemptExercise;
	}

	public ArrayList<LevelStudent> GetLevelStudent() {
		LevelStudent levelStudent = new LevelStudent();
		ArrayList<LevelStudent> listLevelStudent = new ArrayList<LevelStudent>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_LEVEL_STUDENT");
		try {
			while (resultset.next()) {
				levelStudent = new LevelStudent();

				levelStudent.CSC_LEVEL_STUDENT_LEVEL_ID = resultset
						.getInt("CSC_LEVEL_STUDENT_LEVEL_ID");
				levelStudent.CSC_LEVEL_LEVEL_TYPE = resultset
						.getString("CSC_LEVEL_LEVEL_TYPE");
				listLevelStudent.add(levelStudent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listLevelStudent;
	}

	public ArrayList<LevelStudentUser> GetLevelStudentUser() {
		LevelStudentUser levelStudentUser = new LevelStudentUser();
		ArrayList<LevelStudentUser> listLevelStudentUser = new ArrayList<LevelStudentUser>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_LEVEL_STUDENT_USER");
		try {
			while (resultset.next()) {
				levelStudentUser = new LevelStudentUser();

				levelStudentUser.CSC_LEVEL_STU_USER_SURR_KEY = resultset
						.getInt("CSC_LEVEL_STU_USER_SURR_KEY");
				levelStudentUser.CSC_LEVEL_STU_USER_LEVEL_ID = resultset
						.getInt("CSC_LEVEL_STU_USER_LEVEL_ID");
				levelStudentUser.CSC_LEVEL_STU_USER_USER_ID = resultset
						.getInt("CSC_LEVEL_STU_USER_USER_ID");
				listLevelStudentUser.add(levelStudentUser);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listLevelStudentUser;
	}

	public ArrayList<Role> GetRole() {
		Role role = new Role();
		ArrayList<Role> listRole = new ArrayList<Role>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb.GetResultSet("Select * from CSC_ROLE");
		try {
			while (resultset.next()) {
				role = new Role();

				role.Role_ID = resultset.getInt("Role_ID");
				role.Role_Name = resultset.getString("Role_Name");
				role.Role_Description = resultset.getString("Role_Description");
				listRole.add(role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listRole;
	}

	public ArrayList<UserRole> GetUserRole(int UserId) {
		UserRole userRole = new UserRole();
		ArrayList<UserRole> listUserRole = new ArrayList<UserRole>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select distinct * from CSC_USER_ROLE CUR ,CSC_ROLE CR Where CUR.USER_ROLE_ROLE_ID=CR.ROLE_ID and  USER_ROLE_USER_ID="
						+ UserId);
		try {
			while (resultset.next()) {
				userRole = new UserRole();

				userRole.USER_ROLE_USER_ID = resultset
						.getInt("USER_ROLE_USER_ID");
				userRole.USER_ROLE_ROLE_ID = resultset
						.getInt("USER_ROLE_ROLE_ID");
				userRole.CSC_U_R_CLASS_SURR_KEY = resultset
						.getInt("CSC_U_R_CLASS_SURR_KEY");
				userRole.Roles.Role_ID = resultset.getInt("Role_ID");
				userRole.Roles.Role_Name = resultset.getString("Role_Name");
				userRole.Roles.Role_Description = resultset
						.getString("Role_Description");
				listUserRole.add(userRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listUserRole;
	}

	public ArrayList<QbVariable> GetQbVariable() {
		QbVariable qbVariable = new QbVariable();
		ArrayList<QbVariable> listQbVariable = new ArrayList<QbVariable>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_QB_VARIABLE");
		try {
			while (resultset.next()) {
				qbVariable = new QbVariable();

				qbVariable.CSC_QB_VARIABLE_SURR_KEY = resultset
						.getInt("CSC_QB_VARIABLE_SURR_KEY");
				qbVariable.CSC_QB_VARIABLE_ID = resultset
						.getInt("CSC_QB_VARIABLE_ID");
				qbVariable.CSC_QB_VAR_Q_ID = resultset
						.getInt("CSC_QB_VAR_Q_ID");
				qbVariable.CSC_QB_VARIABLE_NAME = resultset
						.getString("CSC_QB_VARIABLE_NAME");
				listQbVariable.add(qbVariable);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listQbVariable;
	}

	public ArrayList<VarParam> GetVarParam() {
		VarParam varParam = new VarParam();
		ArrayList<VarParam> listVarParam = new ArrayList<VarParam>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_VAR_PARM");
		try {
			while (resultset.next()) {
				varParam = new VarParam();

				varParam.CSC_VAR_PARM_SURR_KEY = resultset
						.getInt("CSC_VAR_PARM_SURR_KEY");
				varParam.CSC_VAR_PARM__ID = resultset
						.getInt("CSC_VAR_PARM__ID");
				varParam.CSC_VAR_PARM_VAR_SURR_KEY = resultset
						.getInt("CSC_VAR_PARM_VAR_SURR_KEY");
				varParam.CSC_VAR_PARM_VALUE = resultset
						.getString("CSC_VAR_PARM_VALUE");
				listVarParam.add(varParam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listVarParam;
	}

	public ArrayList<QbParamSet> GetQbParamSet() {
		QbParamSet qbParamSet = new QbParamSet();
		ArrayList<QbParamSet> listQbParamSet = new ArrayList<QbParamSet>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_QB_PARAMETER_SET");
		try {
			while (resultset.next()) {
				qbParamSet = new QbParamSet();

				qbParamSet.CSC_QB_PARAMETER_SET_ID = resultset
						.getInt("CSC_QB_PARAMETER_SET_ID");
				qbParamSet.CSC_QB_PARAMETER_SET_PARM_ID = resultset
						.getInt("CSC_QB_PARAMETER_SET_PARM_ID");
				listQbParamSet.add(qbParamSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listQbParamSet;
	}

	public ArrayList<QaAbParam> GetQaAbParam() {
		QaAbParam qaAbParam = new QaAbParam();
		ArrayList<QaAbParam> listQaAbParam = new ArrayList<QaAbParam>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_QB_PARAMETER_SET");
		try {
			while (resultset.next()) {
				qaAbParam = new QaAbParam();

				qaAbParam.CSC_QBANK_ANSBANK_PARM_SET_ID = resultset
						.getInt("CSC_QBANK_ANSBANK_PARM_SET_ID");
				qaAbParam.CSC_QB_AB_PARM_ANSWER_ID = resultset
						.getInt("CSC_QB_AB_PARM_ANSWER_ID");
				qaAbParam.CSC_QB_AB_PARM_ISCORRECT = resultset
						.getBoolean("CSC_QB_AB_PARM_ISCORRECT");
				listQaAbParam.add(qaAbParam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listQaAbParam;
	}

	public ArrayList<CourseTopic> GetCourseTopic() {
		CourseTopic courseTopic = new CourseTopic();
		ArrayList<CourseTopic> listCourseTopic = new ArrayList<CourseTopic>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_COURSE_TOPIC");
		try {
			while (resultset.next()) {
				courseTopic = new CourseTopic();

				courseTopic.CSC_COURSE_TOPIC_SURR_KEY = resultset
						.getInt("CSC_COURSE_TOPIC_SURR_KEY");
				courseTopic.CSC_COURSE_TOPIC_TOPIC_ID = resultset
						.getInt("CSC_COURSE_TOPIC_TOPIC_ID");
				courseTopic.CSC_COURSE_TOPIC_COURSE_ID = resultset
						.getInt("CSC_COURSE_TOPIC_COURSE_ID");
				listCourseTopic.add(courseTopic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listCourseTopic;
	}

	public ArrayList<ChapterTopic> GetChapterTopic() {
		ChapterTopic chapterTopic = new ChapterTopic();
		ArrayList<ChapterTopic> listChapterTopic = new ArrayList<ChapterTopic>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_CHAPTER_TOPIC");
		try {
			while (resultset.next()) {
				chapterTopic = new ChapterTopic();

				chapterTopic.CSC_CHAPTER_TOPIC_SURR_KEY = resultset
						.getInt("CSC_CHAPTER_TOPIC_SURR_KEY");
				chapterTopic.CSC_CHAPTER_TOPIC_TOPIC_ID = resultset
						.getInt("CSC_CHAPTER_TOPIC_TOPIC_ID");
				chapterTopic.CSC_CHAPTER_TOPIC_Chp_Surr_Key = resultset
						.getInt("CSC_CHAPTER_TOPIC_Chp_Surr_Key");
				listChapterTopic.add(chapterTopic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listChapterTopic;
	}

	public ArrayList<ExerciseTopic> GetExerciseTopic() {
		ExerciseTopic exerciseTopic = new ExerciseTopic();
		ArrayList<ExerciseTopic> listExerciseTopic = new ArrayList<ExerciseTopic>();
		oracleDb.OpenConnection();
		ResultSet resultset = oracleDb
				.GetResultSet("Select * from CSC_EXERCISE_TOPIC");
		try {
			while (resultset.next()) {
				exerciseTopic = new ExerciseTopic();

				exerciseTopic.CSC_EXERCISE_TOPIC_SURR_KEY = resultset
						.getInt("CSC_EXERCISE_TOPIC_SURR_KEY");
				exerciseTopic.CSC_EXERCISE_TOPIC_TOPIC_ID = resultset
						.getInt("CSC_EXERCISE_TOPIC_TOPIC_ID");
				exerciseTopic.CSC_EXERCISE_TOPIC_EXERCISE_ID = resultset
						.getInt("CSC_EXERCISE_TOPIC_EXERCISE_ID");
				listExerciseTopic.add(exerciseTopic);
			}
		} catch (SQLException e) {
		} finally {
			oracleDb.CloseConnection();
		}
		return listExerciseTopic;
	}

	public boolean InsertExerciseTopic(Exercise exercise, Topic topic) {

		String query = "DELETE FROM  CSC_EXERCISE_TOPIC where  CSC_EXERCISE_TOPIC_EXERCISE_ID="
				+ exercise.EXERCISE_ID;
		ResultSet resultset = oracleDb.GetResultSet(query);

		query = "INSERT INTO CSC_EXERCISE_TOPIC (CSC_EXERCISE_TOPIC_SURR_KEY ,CSC_EXERCISE_TOPIC_TOPIC_ID,CSC_EXERCISE_TOPIC_EXERCISE_ID) values(";
		query = query + "CSC_EXERCISE_TOPIC_SEQUENCE.NextVal," + topic.TOPIC_ID
				+ "," + exercise.EXERCISE_ID;
		query = query + ")";
		return oracleDb.InsertQuery(query);
	}

	public ArrayList<Exercise> GetExerciseForCourseId(int courseId) {
		Exercise exercise = new Exercise();
		ArrayList<Exercise> listExercise = new ArrayList<Exercise>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		oracleDb.OpenConnection();
		String query = "select * from CSC_EXERCISE where  EXERCISE_COURSE="
				+ courseId;
		ResultSet resultset = oracleDb.GetResultSet(query);
		try {
			while (resultset.next()) {
				exercise = new Exercise();

				exercise.EXERCISE_ID = resultset.getInt("EXERCISE_ID");
				exercise.EXERCISE_NAME = resultset.getString("EXERCISE_NAME");
				exercise.EXERCISE_COURSE = resultset.getInt("EXERCISE_COURSE");
				exercise.EXERCISE_NAME = resultset.getString("EXERCISE_NAME");
				exercise.EXERCISE_DIFFICULTY_RANGE1 = resultset
						.getInt("EXERCISE_DIFFICULTY_RANGE1");
				exercise.EXERCISE_DIFFICULTY_RANGE2 = resultset
						.getInt("EXERCISE_DIFFICULTY_RANGE2");
				exercise.EXERCISE_RETRYLIMIT = resultset
						.getInt("EXERCISE_RETRYLIMIT");
				exercise.EXERCISE_CORRECTPT = resultset
						.getInt("EXERCISE_CORRECTPT");
				exercise.EXERCISE_PENALTYPT = resultset
						.getInt("EXERCISE_PENALTYPT");
				exercise.EXERCISE_SCORINGTYPE = resultset
						.getInt("EXERCISE_SCORINGTYPE");
				exercise.EXERCISE_CREATEDBY = resultset
						.getInt("EXERCISE_CREATEDBY");
				exercise.EXERCISE_MODIFIEDBY = resultset
						.getInt("EXERCISE_MODIFIEDBY");

				try {
					exercise.EXERCISE_STARTDATE = simpleDateFormat
							.parse(resultset.getString("EXERCISE_STARTDATE"));
					exercise.EXERCISE_ENDDATE = simpleDateFormat
							.parse(resultset.getString("EXERCISE_ENDDATE"));
					exercise.EXERCISE_LASTMODIFIEDDATE = simpleDateFormat
							.parse(resultset
									.getString("EXERCISE_LASTMODIFIEDDATE"));
				} catch (Exception e) {
				}
				listExercise.add(exercise);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listExercise;
	}

	public boolean InsertCourse(Course course) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		String query = "SELECT * FROM CSC_COURSE where CSC_COURSE_TOKEN ='"
				+ course.CSC_COURSE_token + "'";
		ResultSet resultset = oracleDb.GetResultSet(query);
		try {
			if (!resultset.next()) {
				query = "INSERT INTO CSC_COURSE (CSC_COURSE_COURSE_ID , CSC_COURSE_COURSE_NAME ,CSC_COURSE_STARTDATE,CSC_COURSE_ENDDATE,CSC_COURSE_MAX_ENROLL_NO,CSC_COURSE_NUMBER_OF_STUDENTS,CSC_COURSE_TOKEN) "
						+ "values(CSC_COURSE_SEQUENCE.nextval,'"
						+ course.CSC_COURSE_Course_Name
						+ "',"
						+ "to_date('"
						+ simpleDateFormat.format(course.CSC_COURSE_StartDate)
						+ "','YYYY-MM-DD'),"
						+ "to_date('"
						+ simpleDateFormat.format(course.CSC_COURSE_EndDate)
						+ "','YYYY-MM-DD'),"
						+ ""
						+ (course.CSC_COURSE_Max_Enroll_No)
						+ ","
						+ ""
						+ (course.CSC_COURSE_Number_Of_Students)
						+ ","
						+ "'"
						+ (course.CSC_COURSE_token) + "'" + ")";
				if (oracleDb.InsertQuery(query)) {
					// insert into CSC_CLASS
					query = "select Max(CSC_COURSE_COURSE_ID) CSC_COURSE_COURSE_ID from csc_course ";
					resultset = oracleDb.GetResultSet(query);
					if (resultset.next()) {
						int Max_CSC_COURSE_COURSE_ID = resultset
								.getInt("CSC_COURSE_COURSE_ID");
						course.CSC_COURSE_Course_ID = Max_CSC_COURSE_COURSE_ID;
						query = "INSERT INTO CSC_CLASS(CSC_CLASS_SURR_KEY ,CSC_CLASS_CLASS_ID,CSC_CLASS_COURSE_ID) "
								+ "values(CSC_CLASS_SEQUENCE.nextval,1,"
								+ Max_CSC_COURSE_COURSE_ID + ")";
						if (!oracleDb.InsertQuery(query)) {
							// if failure then delete entry into csc_course
							query = "DELETE FROM CSC_COURSE WHERE CSC_COURSE_COURSE_ID "
									+ Max_CSC_COURSE_COURSE_ID;
							oracleDb.InsertQuery(query);
						} else {
							course.CSC_COURSE_LEVEL.CSC_COURSE_LEVEL_SURR_KEY = GetMaxClassSurrogateKey();
							course.CSC_COURSE_LEVEL.CSC_COURSE_LEVEL_Course_ID = Max_CSC_COURSE_COURSE_ID;
							InsertCourseLevel(course.CSC_COURSE_LEVEL);
							InsertCourseTopic(course, course.CourseTopic);
							InsertUserRoleForCourse(course,
									LocalSession.GetCurrentUser(), 1);
							// csc_user_role
							return true;
						}

					}

				}
			} else
				return false;
		} catch (Exception e) {

		}
		return false;

	}

	/**
	 * @param courseTopic
	 */
	private void InsertCourseTopic(Course course, ArrayList<Topic> courseTopic) {

		String query = "";
		for (Topic topic : courseTopic) {
			query = "INSERT INTO CSC_COURSE_TOPIC (CSC_COURSE_TOPIC_SURR_KEY ,CSC_COURSE_TOPIC_TOPIC_ID , CSC_COURSE_TOPIC_COURSE_ID ) "
					+ "VALUES(CSC_COURSE_TOPIC_SEQUENCE.NEXTVAL,"
					+ topic.TOPIC_ID + "," + course.CSC_COURSE_Course_ID + ")";
			try {
				oracleDb.InsertQuery(query);
			} catch (Exception e) {

			}
		}
	}

	private boolean InsertUserRoleForCourse(Course course, User user, int roleId) {
		String query = "INSERT INTO CSC_USER_ROLE(USER_ROLE_USER_ID,USER_ROLE_ROLE_ID,CSC_U_R_CLASS_SURR_KEY) "
				+ "VALUES ("
				+ user.UserId
				+ ","
				+ roleId
				+ ","
				+ course.CSC_COURSE_LEVEL.CSC_COURSE_LEVEL_SURR_KEY + ")";
		if (!oracleDb.InsertQuery(query)) {
			// failure
			return false;
		} else
			return true;

	}

	private boolean InsertCourseLevel(CourseLevel courseLevel) {
		String query = "INSERT INTO CSC_COURSE_LEVEL(CSC_COURSE_LEVEL_SURR_KEY,CSC_COURSE_LEVEL_LEVEL_ID,CSC_COURSE_LEVEL_COURSE_ID) "
				+ "VALUES(CSC_COURSE_LEVEL_SEQUENCE.NEXTVAL,"
				+ courseLevel.CSC_COURSE_LEVEL_LEVEL_ID
				+ ","
				+ courseLevel.CSC_COURSE_LEVEL_Course_ID + ")";
		if (!oracleDb.InsertQuery(query)) {
			// failure
			return false;
		} else
			return true;
	}

	private int GetMaxClassSurrogateKey() {
		String query = "select Max(CSC_CLASS_SURR_KEY) as CSC_CLASS_SURR_KEY from CSC_CLASS ";
		ResultSet resultSet = oracleDb.GetResultSet(query);

		try {
			if (resultSet.next()) {
				return resultSet.getInt("CSC_CLASS_SURR_KEY");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<User> GetReport1() {
		User user = new User();
		ArrayList<User> listUser = new ArrayList<User>();
		oracleDb.OpenConnection();
		String query = " select DISTINCT c.user_fname, c.user_lname from csc_user c, csc_user_role ur, "+ 
				" CSC_EXERCISE ex,CSC_COURSE CO, CSC_CLASS CL , CSC_ROLE R where  "+
				" c.USER_ID = ur.USER_ROLE_USER_ID  "+
				" and ex.EXERCISE_COURSE = CO.CSC_COURSE_COURSE_ID "+
				" and CL.CSC_CLASS_COURSE_ID = CO.CSC_COURSE_COURSE_ID "+
				" and CL.CSC_CLASS_SURR_KEY = UR.CSC_U_R_CLASS_SURR_KEY "+
				" and R.ROLE_ID = ur.USER_ROLE_ROLE_ID "+
				" and CO.CSC_COURSE_TOKEN = 'CSC540FALL14' "+
				" and R.ROLE_NAME = 'Student' "+
				" and c.user_id NOT IN "+
				" (select ua_user_id from csc_user_attempt ua where ua.ua_exercise_id=1) ";
		
		ResultSet resultset = oracleDb
				.GetResultSet(query);
		try {
			while (resultset.next()) {
				user = new User();
				user.UserFName = resultset.getString("USER_FNAME");
				user.UserLName = resultset.getString("USER_LNAME");
				listUser.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listUser;
	}

	public ArrayList<User> GetReport2() {
		User user = new User();
		ArrayList<User> listUser = new ArrayList<User>();
		oracleDb.OpenConnection();
		String query = "select c.user_fname, c.user_lname from csc_user c "+
				"where c.user_id IN ( "+
						"select UA_USER_ID from csc_user_attempt ua  "+
						"where ua.attemp_id = 1 "+
						"and ua.UA_EXERCISE_ID = 1 "+
						"and ua.ua_score = (select max(ua1.ua_score) from csc_user_attempt ua1 "+
						"where ua1.attemp_id=1 and ua1.ua_exercise_id=1) "+
						") ";
		
		ResultSet resultset = oracleDb
				.GetResultSet(query);
		try {
			while (resultset.next()) {
				user = new User();
				user.UserFName = resultset.getString("USER_FNAME");
				user.UserLName = resultset.getString("user_lname");
				listUser.add(user);
			}
		} catch (SQLException e) {
		} finally {
			oracleDb.CloseConnection();
		}
		return listUser;
	}

	public ArrayList<User> GetReport3(int Course_id) {
		User user = new User();
		ArrayList<User> listUser = new ArrayList<User>();
		oracleDb.OpenConnection();
		String query = "select sub.e_name as Exercise_Name, c.user_fname, c.user_lname from csc_user c, "+
						"( "+
						"select ua.UA_USER_ID as u_id, ua.UA_EXERCISE_ID as e_id, EX.EXERCISE_NAME as e_name from csc_user_attempt ua , CSC_EXERCISE EX "+
						"where ua.UA_EXERCISE_ID = EX.EXERCISE_ID "+
						"and ua.attemp_id = 1 "+
						"and EX.EXERCISE_COURSE ="+Course_id+
						"and (ua.UA_EXERCISE_ID, ua.ua_score) IN (select ua1.UA_EXERCISE_ID,max(ua1.ua_score) from csc_user_attempt ua1 "+
						"where ua1.attemp_id=1 "+
						"group by ua1.UA_EXERCISE_ID) "+
						") sub "+
						"where sub.u_id = c.user_id ";
		
		ResultSet resultset = oracleDb
				.GetResultSet(query);
		try {
			while (resultset.next()) {
				user = new User();
				user.ExerciseName = resultset.getString("EXERCISE_NAME");
				user.UserFName = resultset.getString("USER_FNAME");
				user.UserLName = resultset.getString("USER_LNAME");
				listUser.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listUser;
	}

	public ArrayList<User> GetReport4Average(int Course_Id) {
		User user = new User();
		ArrayList<User> listUser = new ArrayList<User>();
		oracleDb.OpenConnection();
		String query = "SELECT CC.USER_FNAME as FirstName,CC.USER_LNAME as LastName, AVG(UA_SCORE) as AVERAGESCORE "
				+ "FROM CSC_USER CC "
				+ "inner join CSC_USER_ROLE CUR on CC.USER_ID=CUR.USER_ROLE_USER_ID AND CUR.USER_ROLE_ROLE_ID=2 "
				+ "left join "
				+ "( SELECT UA_USER_ID,EXERCISE_ID,UA_SCORE FROM "
				+ "( SELECT CUA.UA_USER_ID UA_USER_ID, CE.EXERCISE_ID EXERCISE_ID, UA_SCORE "
				+ " FROM CSC_EXERCISE CE,CSC_USER_ATTEMPT  CUA  "
				+ " WHERE CE.EXERCISE_ID=CUA.UA_EXERCISE_ID and CE.EXERCISE_ScoringType=1 and ROWNUM <= 1 and CE.EXERCISE_COURSE = "+Course_Id
				+ " ORDER BY CUA.UA_USER_ID, CE.EXERCISE_ID,CUA.UA_LASTATTEMPT_DATE DESC) "
				+ " UNION "
				+ " SELECT UA_USER_ID,EXERCISE_ID,UA_SCORE FROM  "
				+ " ( SELECT CUA.UA_USER_ID UA_USER_ID, CE.EXERCISE_ID EXERCISE_ID, UA_SCORE "
				+ " FROM CSC_EXERCISE CE,CSC_USER_ATTEMPT  CUA  "
				+ " WHERE CE.EXERCISE_ID=CUA.UA_EXERCISE_ID and CE.EXERCISE_ScoringType=4 and ROWNUM <= 1 and CE.EXERCISE_COURSE = "+Course_Id
				+ " ORDER BY CUA.UA_USER_ID, CE.EXERCISE_ID,CUA.UA_LASTATTEMPT_DATE ASC) "
				+ " UNION  "
				+ " SELECT UA_USER_ID,EXERCISE_ID,UA_SCORE  "
				+ " FROM ( SELECT CUA.UA_USER_ID, CE.EXERCISE_ID, UA_SCORE  "
				+ " FROM CSC_EXERCISE CE,CSC_USER_ATTEMPT  CUA WHERE CE.EXERCISE_ID=CUA.UA_EXERCISE_ID and CE.EXERCISE_ScoringType=2 and CE.EXERCISE_COURSE = "+Course_Id
				+ " and ROWNUM <= 1 ORDER BY CUA.UA_USER_ID, CE.EXERCISE_ID,CUA.UA_SCORE DESC)  "
				+ " UNION  "
				+ " SELECT DISTINCT CUA.UA_USER_ID, CE.EXERCISE_ID, AVG(UA_SCORE)  "
				+ " FROM CSC_EXERCISE CE,CSC_USER_ATTEMPT  CUA WHERE CE.EXERCISE_ID=CUA.UA_EXERCISE_ID and CE.EXERCISE_ScoringType=3 AND CE.EXERCISE_COURSE = "+Course_Id
				+ " GROUP BY CUA.UA_USER_ID, CE.EXERCISE_ID )  "
				+ " AA ON AA.UA_USER_ID=CC.USER_ID  GROUP BY CC.USER_FNAME,CC.USER_LNAME ";

		ResultSet resultset = oracleDb
				.GetResultSet(query);
		try {
			while (resultset.next()) {
				user = new User();
				user.UserFName = resultset.getString("FirstName");
				user.UserLName = resultset.getString("LastName");
				user.AverageScore = resultset.getDouble("AVERAGESCORE");
				listUser.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listUser;
	}

	public ArrayList<User> GetReport4Total(int Course_id) {
		User user = new User();
		ArrayList<User> listUser = new ArrayList<User>();
		oracleDb.OpenConnection();
		
		String query = " SELECT DISTINCT CE.EXERCISE_ID, CC.USER_FNAME as FirstName,CC.USER_LNAME LastName, CE.EXERCISE_NAME as ExerciseName, UA_SCORE  as TotalScore "+ 
				" FROM CSC_USER CC  "+
				" inner join CSC_USER_ROLE CUR on CC.USER_ID=CUR.USER_ROLE_USER_ID AND CUR.USER_ROLE_ROLE_ID=2  "+
				" left join ( SELECT UA_USER_ID,EXERCISE_ID,UA_SCORE  "+
				" FROM ( SELECT CUA.UA_USER_ID UA_USER_ID, CE.EXERCISE_ID EXERCISE_ID, UA_SCORE "+ 
				" FROM CSC_EXERCISE CE,CSC_USER_ATTEMPT  CUA "+ 
				" WHERE CE.EXERCISE_ID=CUA.UA_EXERCISE_ID and CE.EXERCISE_ScoringType=1 and ROWNUM <= 1 and CE.EXERCISE_COURSE ="+Course_id+
				" ORDER BY CUA.UA_USER_ID, CE.EXERCISE_ID,CUA.UA_LASTATTEMPT_DATE DESC) "+ 
				" UNION "+
				" SELECT UA_USER_ID,EXERCISE_ID,UA_SCORE "+ 
				" FROM ( SELECT CUA.UA_USER_ID UA_USER_ID, CE.EXERCISE_ID EXERCISE_ID, UA_SCORE "+ 
				" FROM CSC_EXERCISE CE,CSC_USER_ATTEMPT  CUA  "+
				" WHERE CE.EXERCISE_ID=CUA.UA_EXERCISE_ID and CE.EXERCISE_ScoringType=4 and ROWNUM <= 1 and CE.EXERCISE_COURSE = "+Course_id+
				" ORDER BY CUA.UA_USER_ID, CE.EXERCISE_ID,CUA.UA_LASTATTEMPT_DATE ASC) "+
				" UNION  "+
				" SELECT UA_USER_ID,EXERCISE_ID,UA_SCORE  "+
				" FROM ( SELECT CUA.UA_USER_ID, CE.EXERCISE_ID, UA_SCORE "+ 
				" FROM CSC_EXERCISE CE,CSC_USER_ATTEMPT  CUA  "+
				" WHERE CE.EXERCISE_ID=CUA.UA_EXERCISE_ID and CE.EXERCISE_ScoringType=2 and ROWNUM <= 1 and CE.EXERCISE_COURSE = "+Course_id+
				" ORDER BY CUA.UA_USER_ID, CE.EXERCISE_ID,CUA.UA_SCORE DESC)  "+
				" UNION  "+
				" SELECT DISTINCT CUA.UA_USER_ID, CE.EXERCISE_ID, AVG(UA_SCORE)  "+
				" FROM CSC_EXERCISE CE,CSC_USER_ATTEMPT  CUA  "+
				" WHERE CE.EXERCISE_ID=CUA.UA_EXERCISE_ID and CE.EXERCISE_ScoringType=3 and CE.EXERCISE_COURSE = "+Course_id+
				" GROUP BY CUA.UA_USER_ID, CE.EXERCISE_ID ) AA ON AA.UA_USER_ID=CC.USER_ID  "+
				" INNER JOIN csc_exercise CE on CE.EXERCISE_ID=AA.EXERCISE_ID ";

		ResultSet resultset = oracleDb
				.GetResultSet(query);
		try {
			while (resultset.next()) {
				user = new User();
				user.UserFName = resultset.getString("FirstName");
				user.UserLName = resultset.getString("LastName");
				user.ExerciseName = resultset.getString("ExerciseName");
				user.AverageScore = resultset.getDouble("TotalScore");
				listUser.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listUser;
	}

	public ArrayList<Exercise> GetReport5(int Course_id) {
		Exercise exercise = new Exercise();
		ArrayList<Exercise> listExercise = new ArrayList<Exercise>();
		oracleDb.OpenConnection();
		String query = "Select CE.EXERCISE_ID AS EXERCISE_ID, CE.EXERCISE_NAME as EXERCISE_NAME,AA.AVERAGEATTEMPT "+
				" as AVERAGEATTEMPT  FROM  csc_exercise CE "+
				" LEFT JOIN  (SELECT ua_exercise_id, "+
				" avg(Attempt) AVERAGEATTEMPT  from ( select ua_exercise_id,UA_USER_ID,count(*) as Attempt "+
				" from csc_user_attempt ua "+
				" Group By ua.ua_exercise_id, ua.UA_USER_ID) A group by ua_exercise_id ) AA  on AA.ua_exercise_id=CE.exercise_id "+
				" where CE.EXERCISE_COURSE ="+Course_id;
		
		ResultSet resultset = oracleDb
				.GetResultSet(query);
		try {
			while (resultset.next()) {
				exercise = new Exercise();
				exercise.EXERCISE_ID = resultset.getInt("EXERCISE_ID");
				exercise.EXERCISE_NAME = resultset.getString("EXERCISE_NAME");
				exercise.AverageAttempt = resultset.getInt("AVERAGEATTEMPT");
				listExercise.add(exercise);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			oracleDb.CloseConnection();
		}
		return listExercise;
	}


	public boolean IsRoleOnCourse(User user, Course course, int roleId) {
		String query = "SELECT count(*) counter FROM CSC_COURSE CCO "
				+ "inner join CSC_CLASS CCS on CCO.CSC_COURSE_COURSE_ID=CCS.CSC_CLASS_COURSE_ID "
				+ "inner join csc_user_role CUR on CUR.CSC_U_R_CLASS_SURR_KEY=CCS.CSC_CLASS_COURSE_ID "
				+ "WHERE CUR.User_ROLE_USER_ID=" + user.UserId
				+ " and CUR.USER_ROLE_ROLE_ID =" + roleId
				+ " and CCS.CSC_CLASS_COURSE_ID ="
				+ course.CSC_COURSE_Course_ID;
		try {
			ResultSet resultset = oracleDb.GetResultSet(query);
			while (resultset.next()) {
				return resultset.getInt("counter") > 0 ? true : false;
			}
		} catch (Exception e) {

		}
		return false;
	}
	
	public boolean AddTAToCourse(String token,User user)
	{
		ArrayList<Course> listCourse=GetCourseForToken(token);
		for(Course course:listCourse)
		{
			return InsertUserRoleForCourse(course,user,3);
		}
		return false;
		
	}
	
	
	
	
}
