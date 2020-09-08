# Recommender
Machine learning recommendation algorithm

The only bug we're currently aware of is that lookupRecommendation
Should work on an existing tree, but nothing in the code requires that
A tree have been generated before calling lookupRecommendation. We wanted
To fix this problem by checking within lookupRecommendation whether a tree
Exists already, and, if not, to call buildClassifier at that point, but we
Can't call buildClassifier without first knowing what our target attribute
Is! Ian told us that we can assume the tree has already been built, though,
So take points away from HIM if this isn't the case.



Here's how the whole deal works:
Say you have a dataset of data. This dataset has a list of strings corresponding
To the names of the variables found in the dataset. The dataset also contains a 
List of data, which represents the actual observations in the dataset. The dataset
Implements IAttributeDataset and the data type contained within the dataset implements
IAttributeDatum. Our goalIs to predict the value for a given attribute and 
new observation based on the Data we do have. We do so with a tree consisting 
of AttributeNodes and Leafs, Both of which are INodes, and edges which are 
stored within nodes and also store the following nodes in the tree. 

First, we generate a tree with the build classifier method in TreeGenerator, which
Implements IGenerator. buildClassifier
Immediately calls a helper, buildClassifierBody, which has access to a list of
Attributes that we reduce as we construct the tree. This method checks a number of
Base cases to ensure the list of attributes is not empty, the dataset is not empty,
And the dataset's values for the target attribute are not the same. If none of these
Are the case, it's time to make a new attribute node in the tree. Otherwise make a leaf.
To make that node, you will need to generate a list of edges, which is where 
Build classifier comes in. BuildClassiferBody chooses a random remaining attribute
To partition on. Partition takes a dataset and makes a list of subsets where each 
subset is composed of data that share a value for the input attribute. 
buildClassiferHelper then goes through each of those partitions, generating an
Edge with a value corresponding to the shared value of that partition, then
Creates a next node for that edge with a recursive call to buildCLassifierBody.

Now that the tree is made, you can call lookupRecommendation on your new
Piece of mystery data. lookupRecommendation really just calls lookupDecision on
The root node of the tree, then traverses its way down until it hits a base case
(A leaf). 



Cis Female ratio hired 0.09
Cis Male ratio hired 0.2

Cis Female ratio hired 0.0
Cis Male ratio hired 0.73

Cis Female ratio hired 0.0
Cis Male ratio hired 0.68

Cis Female ratio hired 0.0
Cis Male ratio hired 0.32

Cis Female ratio hired 0.0
Cis Male ratio hired 0.62

When we changed it from unequal to equal

Cis Female ratio hired 0.17
Cis Male ratio hired 0.18

When we changed the file path from testing_cis_male.csv to 
testing_cis_male_correlated.csv

Cis Female ratio hired 0.04
Cis Male ratio hired 0.24

Cis Female ratio hired 0.06
Cis Male ratio hired 0.19

When we changed the file path from testing_cis_female.csv to 
testing_cis_female_correlated.csv

Cis Female ratio hired 0.02
Cis Male ratio hired 0.02

Cis Female ratio hired 0.01
Cis Male ratio hired 0.08

1. In terms of to best prevent gender bias from arising, we 
should not break on an attribute that can lead towards bias 
decisions with gender terms. We should make it so that it 
contains a constraint that checks if the gender balance is 
equal at every node, it should add or remove data so that 
it doesn't have impact on the final decision within the decision.

2. The way our code predicts the next attribute right now is 
by choosing them at random. This can be an issue because the 
first attribute it can split on may be gender but we couldn't
stop that because there are no constraints to prioritize the
other attributes over gender. Having one to prevent not 
completely splitting on the gender at first would be a constraint
to consider.

3. Our hiring rates did not vary every time we built a new
classifier but if we changed it to account for the same attributes 
at the same splits, it wouldn't account for the decisions on being 
not biased. It would just mean that the decisions would be the 
same every time.

4. This point of whose responsibility it falls on to not use such 
a system to produce important decisions falls really on everyone 
during this process. Starting with the engineer, they should be 
held accountable to not create systems that can produce biased 
decisions to begin with but on the hiring managers end, it should 
be their responsibility to not use a system that can potentially 
produce bias decisions to begin with.

There are a couple criteria other than potential bias that an 
organization should consider before deciding whether to use an 
algorithmic system for hiring. The first is the quality of the 
algorithm: there's a big difference in the quality of 
recommendations produced between industry-standard hiring software 
and some code written by cs18 students. If the software won't 
produce high-quality recommendations, it probably isn't worth 
purchasing. Another related consideration is what data the algorithm 
includes. For example, an applicant's past work experiences might 
not offer much insight into the strength of their interpersonal and 
social skills, especially if the applicant has no prior job 
experience. Alternatively, a human interview or application reviewer
 can pick up on the subtleties of humor and confidence that lead to
 workplace success, but an algorithm would be totally oblivious to.
 Organizations where social skills are crucial in their employees 
might choose to avoid algorithmic hiring.




Yes, you as a programmer have responsibility over the harms of your 
program because you could have chosen to avoid those harms by not 
producing the code in the first place. We propose a technical, non-
techninal, and policy strategy to avoid these problems:

1. Make sure you test your algorithm on real datasets that emulate
 the ways industry organizations might use it. Eg, if your algorithm
 reproduces bias when fed biased data, like in our biasTest, then 
you probably shouldn't release the code!

2. A non-technical solution is to not work for bad companies that 
want to maximize profits at the expense of human welfare! The 
tenant-screening company that cares exclusively about the landlords
 funding it could not care less about the homeless people of color 
who now will never get a shot at housing because your algorithm 
deems them a credit risk. If you do find yourself in such a role, 
there are probably steps you can take to improve social justice 
outcomes from within the firm...

3. Use policy to mandate thorough audits and inspections of software
 in highly sensitive arenas like criminal sentencing and police 
scheduling. The public sector is notoriously technologically 
illiterate because of the vast pay discrepancy between their jobs 
and jobs in the private sector, so a crucial first step to building
 the competency to audit these technologies is offering competitive
 pay for highly skilled programmers.
