import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";


const Register = () => {
  const [questions, setQuestions] = useState([]);

  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    password: "",
    salary: "",
    profession: "",
    securityAnswers: [
      {
        questionId: "",
        answer: "",
      },
      {
        questionId: "",
        answer: "",
      },
      {
        questionId: "",
        answer: "",
      },
    ],
  });

  useEffect(() => {
    fetchSecurityQuestions();
  }, []);

  const fetchSecurityQuestions = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/v1/auth/security-questions"
      );

      setQuestions(response.data.data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleQuestionChange = (index, value) => {
    const updatedAnswers = [...formData.securityAnswers];

    updatedAnswers[index] = {
      ...updatedAnswers[index],
      questionId: value,
    };

    setFormData({
      ...formData,
      securityAnswers: updatedAnswers,
    });
  };

  const handleAnswerChange = (index, value) => {
    const updatedAnswers = [...formData.securityAnswers];

    updatedAnswers[index] = {
      ...updatedAnswers[index],
      answer: value,
    };

    setFormData({
      ...formData,
      securityAnswers: updatedAnswers,
    });
  };

  const getAvailableQuestions = (currentIndex) => {
    const selectedIds = formData.securityAnswers
      .filter((_, index) => index !== currentIndex)
      .map((item) => item.questionId)
      .filter(Boolean);

    return questions.filter(
      (question) =>
        !selectedIds.includes(question.id.toString())
    );
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const payload = {
        ...formData,
        securityAnswers: formData.securityAnswers.map((item) => ({
          questionId: Number(item.questionId),
          answer: item.answer,
        })),
      };

      const response = await axios.post(
        "http://localhost:8080/api/v1/auth/register",
        payload
      );

      alert(response.data.message);
    } catch (error) {
      if (error.response) {
        alert(error.response.data.message);
      } else {
        alert("Something went wrong");
      }
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-600 via-purple-600 to-pink-600 flex items-center justify-center px-4 py-10">
      <div className="w-full max-w-2xl bg-white rounded-2xl shadow-2xl p-8">
        <h1 className="text-3xl font-bold text-center mb-8">
          Create Account
        </h1>

        <form onSubmit={handleSubmit} className="space-y-5">
          <input
            type="text"
            name="fullName"
            placeholder="Full Name"
            value={formData.fullName}
            onChange={handleChange}
            className="w-full px-4 py-3 border rounded-xl"
            required
          />

          <input
            type="email"
            name="email"
            placeholder="Email"
            value={formData.email}
            onChange={handleChange}
            className="w-full px-4 py-3 border rounded-xl"
            required
          />

          <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            className="w-full px-4 py-3 border rounded-xl"
            required
          />

          <input
            type="number"
            name="salary"
            placeholder="Salary"
            value={formData.salary}
            onChange={handleChange}
            className="w-full px-4 py-3 border rounded-xl"
            required
          />

          <input
            type="text"
            name="profession"
            placeholder="Profession"
            value={formData.profession}
            onChange={handleChange}
            className="w-full px-4 py-3 border rounded-xl"
            required
          />

          <div className="mt-8">
            <h2 className="text-xl font-semibold mb-4">
              Security Questions
            </h2>

            {formData.securityAnswers.map((item, index) => (
              <div
                key={index}
                className="border rounded-xl p-4 mb-4 bg-gray-50"
              >
                <label className="block mb-2 font-medium">
                  Question {index + 1}
                </label>

                <select
                  value={item.questionId}
                  onChange={(e) =>
                    handleQuestionChange(index, e.target.value)
                  }
                  className="w-full px-4 py-3 border rounded-xl mb-3"
                  required
                >
                  <option value="">
                    Select Security Question
                  </option>

                  {getAvailableQuestions(index).map((question) => (
                    <option
                      key={question.id}
                      value={question.id}
                    >
                      {question.question}
                    </option>
                  ))}
                </select>

                <input
                  type="text"
                  placeholder="Your Answer"
                  value={item.answer}
                  onChange={(e) =>
                    handleAnswerChange(index, e.target.value)
                  }
                  className="w-full px-4 py-3 border rounded-xl"
                  required
                />
              </div>
            ))}
          </div>

          <button
            type="submit"
            className="w-full py-3 text-white font-semibold rounded-xl bg-indigo-600 hover:bg-indigo-700"
          >
            Create Account
          </button>
        </form>
        <p className="text-center text-sm text-gray-500 mt-6">
            Already have an account?
            <Link
                to="/login"
                className="text-indigo-600 font-medium ml-1"
            >
                Login
            </Link>
            </p>
      </div>
    </div>
  );
};

export default Register;