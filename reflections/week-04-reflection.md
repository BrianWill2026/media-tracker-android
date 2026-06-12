# Week {{3}} Reflection

**Name: Brian W**
**Date: 6/4**

---

## Commits This Week

**Link: https://github.com/BrianWill2026/media-tracker-android/pull/5**

---

## Code Review

**Reviewed:** Abduranhman 
**Link to my review:** https://github.com/abduramanjigre/media-tracker-android/pull/5#pullrequestreview-4481976572

Looks like he pulled some stuff from the profs branch but other wise it looks like mine. There will probably need to be some hashing out to make sure it is working correctly.



### What I Looked At

I looked at the RegisterScreen.kt and the RegisterViewModel.kt and how the data in those fields are being handled.

### What I Noticed

I noticed we are moving from a val to a var which I want to ask next week on the specifics of why.

### Comments I Left

I Noticed he pulled from the main repository and not his own.

## One Thing I Understood More Deeply

I understood more about how certain things can be loaded in different states and the importance on how you load them. For example password.
Below is some of the things that I understood more on but wish to have more clarity

var email       by remember {mutableStateOf("") }
private val _displayName = MutableStateFlow("")
val displayName: StateFlow<String> = _displayName.asStateFlow()

## One Thing I'm Still Confused About

I think that I just am wondering where the handoff to the web is made but I know we are not there yet.

## Anything Else *(optional)*

n/a

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
