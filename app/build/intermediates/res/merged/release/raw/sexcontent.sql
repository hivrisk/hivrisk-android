INSERT INTO `sexactiv` (`ID`, `PRA`, `KND`, `MM`, `MF`, `FM`, `FF`, `SYM`, `PRO`, `EYC`, `FLD`, `SLV`, `HIVUR`, `HIVPR`, `HEPB`, `HPAP`, `HERV`, `GONO`, `SIFI`, `PARA`, `FEOR`, `CLAM`, `BACT`)
SELECT 1 , 'ORAL_PEN_ACT', 'ORAL', 1, 0, 1, 0,   0, 1, 1, 0, 1,  2,  0, 1,1,1,2,1,0,0,1,0 UNION
SELECT 2 , 'ORAL_PEN_REC', 'ORAL', 1, 1, 0, 0,   0, 1, 1, 0, 1,  1,  0, 0,0,1,1,1,0,0,1,0 UNION
SELECT 3 , 'ORAL_ANL_ACT', 'ORAL', 1, 1, 1, 0,   0, 2, 0, 1, 1,  1,  0, 0,0,0,0,0,0,1,0,1 UNION
SELECT 4 , 'ORAL_ANL_REC', 'ORAL', 1, 1, 1, 0,   0, 2, 0, 1, 1,  1,  0, 0,0,1,0,0,0,0,0,0 UNION
SELECT 5 , 'ORAL_VAG_ACT', 'ORAL', 0, 1, 0, 1,   0, 2, 0, 1, 1,  1,  0, 0,0,1,0,0,0,1,0,0 UNION
SELECT 6 , 'ORAL_VAG_REC', 'ORAL', 0, 0, 1, 1,   0, 2, 0, 1, 1,  0,  0, 0,0,1,0,0,0,0,1,1 UNION
SELECT 7 , 'ANAL_PEN_ACT', 'PENT', 1, 1, 0, 0,   0, 1, 1, 0, 0,  3,  1, 1,1,1,1,2,3,0,1,1 UNION
SELECT 8 , 'ANAL_PEN_REC', 'PENT', 1, 0, 1, 0,   0, 1, 1, 0, 0,  4,  1, 1,1,1,1,1,1,0,1,0 UNION
SELECT 9 , 'VAGL_PEN_ACT', 'PENT', 0, 1, 0, 0,   0, 1, 1, 1, 0,  4,  1, 1,1,1,2,1,1,0,3,1 UNION
SELECT 10, 'VAGL_PEN_REC', 'PENT', 0, 0, 1, 0,   0, 1, 1, 1, 0,  4,  1, 4,2,1,4,1,1,0,3,1 UNION
SELECT 11, 'FIST_ANL_ACT', 'PENT', 1, 1, 1, 1,   0, 3, 0, 1, 0,  1,  0, 1,0,0,0,0,0,1,0,0 UNION
SELECT 12, 'FIST_ANL_REC', 'PENT', 1, 1, 1, 1,   0, 3, 0, 1, 0,  1,  0, 0,0,0,0,0,0,0,0,0 UNION
SELECT 13, 'HAND_PEN_ACT', 'HJOB', 1, 0, 1, 0,   0, 1, 1, 0, 0,  0,  0, 0,0,0,0,0,0,0,0,0 UNION
SELECT 14, 'HAND_PEN_REC', 'HJOB', 1, 1, 0, 0,   0, 1, 1, 0, 0,  0,  0, 0,0,0,0,0,0,0,0,0 UNION
SELECT 15, 'HAND_VAG_ACT', 'HJOB', 0, 1, 0, 1,   0, 2, 0, 1, 0,  1,  0, 0,0,0,0,0,0,0,0,0 UNION
SELECT 16, 'HAND_VAG_REC', 'HJOB', 0, 0, 1, 1,   0, 2, 0, 1, 0,  1,  0, 0,0,0,0,0,0,0,1,1 UNION
SELECT 17, 'FROT_VAG_VAG', 'FROT', 0, 0, 0, 1,   1, 2, 0, 1, 1,  1,  0, 0,0,1,1,0,0,0,1,1 UNION
SELECT 18, 'FROT_PEN_PEN', 'FROT', 1, 0, 0, 0,   1, 1, 1, 1, 1,  1,  0, 0,0,1,1,0,0,0,0,0 UNION
SELECT 19, 'FROT_CUD_HUG', 'FROT', 1, 1, 1, 1,   1, 0, 1, 1, 1,  0,  0, 0,0,0,0,0,0,0,0,0 UNION
SELECT 20, 'KISS_KIS_KIS', 'FROT', 1, 1, 1, 1,   1, 0, 0, 0, 1,  0,  0, 0,0,1,0,0,0,0,0,0;
