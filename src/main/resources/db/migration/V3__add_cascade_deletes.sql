-- 1. Update applications FK (adopter_id -> adopter_profiles.id)
ALTER TABLE applications 
DROP CONSTRAINT IF EXISTS fk_application_adopter;

ALTER TABLE applications 
ADD CONSTRAINT fk_application_adopter 
FOREIGN KEY (adopter_id) REFERENCES adopter_profiles(id) ON DELETE CASCADE;

-- 2. Update adopter_profiles FK (user_id -> users.id)
ALTER TABLE adopter_profiles 
DROP CONSTRAINT IF EXISTS FKsi7c54bb4cv0urvxnl5ws6ipt;

ALTER TABLE adopter_profiles 
ADD CONSTRAINT fk_adopter_profiles_user 
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

-- 3. Update shelter_profiles FK (user_id -> users.id)
ALTER TABLE shelter_profiles 
DROP CONSTRAINT IF EXISTS FKrinjvo95r9ixst4l7a3eemuyr;

ALTER TABLE shelter_profiles 
ADD CONSTRAINT fk_shelter_profiles_user 
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;