//package com.ideahub.ideahubbackend.service;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.ai.chat.ChatClient;
//import org.springframework.ai.chat.ChatResponse;
//import org.springframework.ai.chat.prompt.Prompt;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AiService {
//
//   private static final Logger logger = LoggerFactory.getLogger(AiService.class);
//   private final ChatClient chatClient;
//
//   public AiService(ChatClient chatClient) {
//       this.chatClient = chatClient;
//   }
//
//   @Async
//   public CompletableFuture<String> generateConclusionAsync(String ideaTopic, String ideaSummary, String ideaExplanation) {
//       try {
//           String promptText = """
//               You are an expert reviewer. A user submitted an idea.
//
//               Topic: %s
//               Summary: %s
//               Explanation: %s
//
//               Please provide a detailed, critical review of this idea.
//               Point out strengths, weaknesses, possible improvements, and evidence if possible.
//               Be specific and constructive. Avoid only praise; include critical points.
//               """.formatted(ideaTopic, ideaSummary, ideaExplanation);
//
//           logger.info("Generating AI conclusion for idea: {}", ideaTopic);
//
//           ChatResponse response = chatClient.call(new Prompt(promptText));
//           if (response.getResult() == null || response.getResult().getOutput() == null) {
//               logger.error("Received null response from AI service for idea: {}", ideaTopic);
//               return CompletableFuture.completedFuture("AI analysis temporarily unavailable. Please try again later.");
//           }
//           String result = response.getResult().getOutput().getContent();
//
//           logger.info("Successfully generated AI conclusion for idea: {}", ideaTopic);
//           return CompletableFuture.completedFuture(result);
//
//       } catch (Exception e) {
//           logger.error("Error generating AI conclusion for idea: {}", ideaTopic, e);
//           return CompletableFuture.completedFuture("AI analysis temporarily unavailable. Please try again later.");
//       }
//   }
//
//   public String generateConclusion(String ideaTopic, String ideaSummary, String ideaExplanation) {
//       try {
//           // Use async method with timeout
//           CompletableFuture<String> future = generateConclusionAsync(ideaTopic, ideaSummary, ideaExplanation);
//           return future.get(30, TimeUnit.SECONDS); // 30 second timeout
//       } catch (TimeoutException e) {
//           logger.warn("AI service timeout for idea: {}. Using fallback response.", ideaTopic);
//           return "AI analysis timed out. Your idea has been saved without AI review.";
//       } catch (Exception e) {
//           logger.error("Error in AI service for idea: {}", ideaTopic, e);
//           return "AI analysis temporarily unavailable. Please try again later.";
//       }
//   }
//}

//MOCK AI CONCLUSION FOR TESTING

 package com.ideahub.ideahubbackend.service;

 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.stereotype.Service;

 @Service
 public class AiService {

     private static final Logger logger = LoggerFactory.getLogger(AiService.class);

     // 🚫 Removed ChatClient dependency (since we're mocking AI)

     public String generateConclusion(String ideaTopic, String ideaSummary, String ideaExplanation) {
         logger.info("Mocking AI conclusion for idea: {}", ideaTopic);

         // Return a fake response just for testing
         return "Mock AI Review (no credits used):\n"
                 + "Topic: " + ideaTopic + "\n"
                 + "Summary feedback: The summary is clear but could use more precision.\n"
                 + "Critical note: Explanation lacks evidence for claims.\n"
                 + "Strength: Philosophically interesting and worth deeper exploration.";
     }
 }

