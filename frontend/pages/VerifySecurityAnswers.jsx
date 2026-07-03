import { useState } from "react";
import axios from "axios";

const VerifySecurityAnswers = () => {
  const email = sessionStorage.getItem("email");

  const questions = JSON.parse(
    sessionStorage.getItem("securityQuestions") || "[]"
  );

  const [answers, setAnswers] = useState(
    questions.map((question) => ({
      questionId: question.questionId,
      answer: "",
    }))
  );

  const handleChange = (index, value) => {
    const updated = [...answers];

    updated[index].answer = value;

    setAnswers(updated);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/auth/verify-security-answers",
        {
          email,
          answers,
        }
      );

      sessionStorage.setItem(
        "resetToken",
        response.data.data.resetToken
      );

      window.location.href = "/reset-password";
    } catch (error) {
      alert(error.response?.data?.message || "Invalid answers");
    }
  };

  return (
    <div className="min-h-screen flex justify-center items-center bg-indigo-100">
      <div className="bg-white p-8 rounded-xl w-full max-w-xl shadow-lg">
        <h2 className="text-2xl font-bold mb-6">
          Verify Security Questions
        </h2>

        <form onSubmit={handleSubmit}>
          {questions.map((question, index) => (
            <div
              key={question.questionId}
              className="mb-5"
            >
              <label className="block mb-2">
                {question.question}
              </label>

              <input
                type="text"
                className="w-full border p-3 rounded-lg"
                value={answers[index]?.answer}
                onChange={(e) =>
                  handleChange(index, e.target.value)
                }
                required
              />
            </div>
          ))}

          <button
            className="w-full p-3 bg-indigo-600 text-white rounded-lg"
            type="submit"
          >
            Verify Answers
          </button>
        </form>
      </div>
    </div>
  );
};

export default VerifySecurityAnswers;